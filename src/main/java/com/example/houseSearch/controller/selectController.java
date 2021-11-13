package com.example.houseSearch.controller;


import com.example.houseSearch.bean.House;
import com.example.houseSearch.service.orderService;
import com.example.houseSearch.service.searchService;
import com.example.houseSearch.service.selectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Priority;
import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

@Controller
public class selectController {

    final int[] Wt = new int[]{ 10,3,1};  // 权重


    @Resource
    searchService searchService;

    @Resource
    selectService  selectService;


    @ResponseBody
    @RequestMapping(value = "/selectByPrice",method = RequestMethod.GET)
    public String selectPrice( @ModelAttribute("price") int price){


        String destination = searchService.getWhere();
        Date date = searchService.getDate();

        ArrayList<House> result = selectService.searchByPrice(price,destination,date);

        if(result.isEmpty())
            return "No suitable result!";
        else {
            StringBuilder ret = new StringBuilder("结果如下:" + "<br>");

            for(House house : result){

                ret.append("房名: " ).append(house.getName()).append(" ");
                ret.append("价格: ").append( house.getPrice() ).append(" 元/月").append(" <br>");
                ret.append("面积: ").append( house.getSize()).append(" ㎡").append(" <br>");
                ret.append("楼高：").append( house.getHeight()).append("层 <br>");
                ret.append("户型: ").append( house.getShape()).append(" <br>");
                ret.append("位置: ").append(house.getPosition()).append(" <br>");
                ret.append("距离 ").append(house.getDestination()).append(" ").append(house.getDistance()).append("米<br>");
                ret.append("------------------------------------<br>");

            }
            return ret.toString();

        }


    }


    @ResponseBody
    @RequestMapping(value = "/selectByShape",method = RequestMethod.GET)
    public String searchShape(@ModelAttribute("bedroom") String bedroom,@ModelAttribute("livingroom") String livingroom,@ModelAttribute("bathroom") String bathroom){


        String destination = searchService.getWhere();
        Date date = searchService.getDate();

        String shape = bedroom + "室" + livingroom +"厅"+ bathroom +"卫";

        ArrayList<House> result = selectService.searchByShape(shape,destination,date);

        if(result.isEmpty())
            return "No suitable result!";
        else {
            StringBuilder ret = new StringBuilder("结果如下:" + "<br>");

            for(House house : result){

                ret.append("房名: " ).append(house.getName()).append(" ");
                ret.append("价格: ").append( house.getPrice() ).append(" 元/月").append(" <br>");
                ret.append("面积: ").append( house.getSize()).append(" ㎡").append(" <br>");
                ret.append("楼高：").append( house.getHeight()).append("层 <br>");
                ret.append("户型: ").append( house.getShape()).append(" <br>");
                ret.append("位置: ").append(house.getPosition()).append(" <br>");
                ret.append("距离 ").append(house.getDestination()).append(" ").append(house.getDistance()).append("米<br>");
                ret.append("------------------------------------<br>");

            }
            return ret.toString();

        }

    }

    @ResponseBody
    @RequestMapping(value = "/evaluate",method = RequestMethod.GET)
    public String evaluateHouse(){

        String destination = searchService.getWhere();
        Date date = searchService.getDate();

        // 拿到此次搜索出的所有数据,输入
        ArrayList<House> this_all = selectService.getThisAll(date,destination);

        float[][] input = new float[this_all.size()][3];

        float sum_dis = 0, sum_high=0;
        // [价格/面积，距离，楼高]
        for( int i=0;i<this_all.size();i++){

            input[i][0] = this_all.get(i).getPrice() / this_all.get(i).getSize();

            input[i][1] = (float) this_all.get(i).getDistance();

            sum_dis += (float) this_all.get(i).getDistance();

            input[i][2] = this_all.get(i).getHeight();

            sum_high +=  this_all.get(i).getHeight();

        }

        float avg_dis = sum_dis / this_all.size();

        int avg_high = (int) (sum_high / this_all.size());


        int[][] hidden = new int[input.length][3];

        for(int i=0;i<hidden.length;i++){

            // 根据距离得到以前
            ArrayList<House> odd = selectService.getOddByDistance(input[i][1],date,destination);


            // 计算平均价格/面积
            int avg_price = 0 , sum_price_size=0;

            if(odd.isEmpty())
                avg_price = (int) input[i][0];

             else {
                for (House house : odd)
                    sum_price_size += house.getPrice() / house.getSize();

                    avg_price = sum_price_size / odd.size();
            }

            if( input[i][0] <= avg_price)
                hidden[i][0] = 5;
            else
                hidden[i][0] = 0;


            // 距离
            if( input[i][1] <= avg_dis)
                hidden[i][1] = 2;
            else
                hidden[i][1] = 1;

            // 楼高
            if(input[i][2] <= avg_high)
                hidden[i][2] = 1;
            else
                hidden[i][2] = 0;


        }

        // 根据权重得到输出
        int[] output = new int[hidden.length];


        int sum = 0;
        for(int i=0;i<output.length;i++) {
            output[i] = hidden[i][0] * Wt[0] + hidden[i][1] * Wt[1] + hidden[i][2] * Wt[2];
            sum += output[i];
        }

        // 计算概率
        float[] out_pos = new float[output.length];
     //   HashMap<Integer,Float> hashMap = new HashMap<>();

        PriorityQueue<float[]> priorityQueue = new PriorityQueue<>(
                (float[] pair1,float[] pair2)->{

                    if( pair2[1] > pair1[1])
                        return 1;
                    else if( pair2[1] == pair1[1])
                        return 0;
                    else return -1;

                }
        );

        for(int i=0;i<out_pos.length;i++){
            out_pos[i] =(float) output[i] / sum;
          //  hashMap.put(i,out_pos[i]);
            priorityQueue.add(new float[]{i,out_pos[i]});
        }


        StringBuilder ret = new StringBuilder("结果如下:" + "<br>");



        while ( !priorityQueue.isEmpty()){
            float[] pair = priorityQueue.poll();

            int i= (int) pair[0];
            ret.append("房名: " ).append(this_all.get(i).getName()).append(" ");
            ret.append("价格: ").append( this_all.get(i).getPrice() ).append(" 元/月").append(" <br>");
            ret.append("面积: ").append( this_all.get(i).getSize()).append(" ㎡").append(" <br>");
            ret.append("楼高：").append( this_all.get(i).getHeight()).append("层 <br>");
            ret.append("户型: ").append( this_all.get(i).getShape()).append(" <br>");
            ret.append("位置: ").append(this_all.get(i).getPosition()).append(" <br>");
            ret.append("距离 ").append(this_all.get(i).getDestination()).append(" ").append(this_all.get(i).getDistance()).append("米<br>");
            if(pair[1] >= 0.8 )
                ret.append("5星推荐!<br> ");
            else if( pair[1] >= 0.6 && pair[1] < 0.8)
                ret.append("4星推荐！<br>");
            else if( pair[1] >= 0.4 && pair[1] <0.6)
                ret.append("3星推荐！<br>");
            else if( pair[1] >= 0.2 && pair[1] < 0.4)
                ret.append("2星推荐！<br>");
            else
                ret.append("1星推荐！<br>");

            ret.append("------------------------------------<br>");
        }



        return ret.toString();

    }

}
