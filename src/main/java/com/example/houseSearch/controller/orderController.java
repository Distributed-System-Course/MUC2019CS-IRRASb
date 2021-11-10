package com.example.houseSearch.controller;

import com.example.houseSearch.bean.House;
import com.example.houseSearch.service.searchService;
import com.example.houseSearch.serviceImpl.orderServiceImpl;
import com.example.houseSearch.serviceImpl.searchServiceImpl;
import com.example.houseSearch.service.searchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;

@Controller
public class orderController {

    @Resource
    orderServiceImpl orderService;

    @Resource
     searchService searchService;
  //  searchServiceImpl searchService ;

    @ResponseBody
    @RequestMapping(value = "/orderByPrice",method = RequestMethod.GET)
    public String orderPrice(@ModelAttribute("select") String flag){

//        String destination = searchService.getNow();
//        Date date = searchService.getToday();
        String destination = searchService.getWhere();
        Date date = searchService.getDate();


        ArrayList<House> result = orderService.orderByPrice(flag,destination,date);

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

    @ResponseBody
    @RequestMapping(value = "/orderByDistance",method = RequestMethod.GET)
    public String orderDistance( @ModelAttribute("select") String flag){

//        String destination = searchService.getNow();
//        Date date = searchService.getToday();
        String destination = searchService.getWhere();
        Date date = searchService.getDate();


        ArrayList<House> result = orderService.orderByDistance(flag,destination,date);

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

    @ResponseBody
    @RequestMapping(value = "/orderBySize",method = RequestMethod.GET)
    public String orderSize( @ModelAttribute("select") String flag){

//        String destination = searchService.getNow();
//        Date date = searchService.getToday();
        String destination = searchService.getWhere();
        Date date = searchService.getDate();


        ArrayList<House> result = orderService.orderBySize(flag,destination,date);

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

    @ResponseBody
    @RequestMapping(value = "/selectByShape",method = RequestMethod.GET)
    public String searchShape(@ModelAttribute("bedroom") String bedroom,@ModelAttribute("livingroom") String livingroom,@ModelAttribute("bathroom") String bathroom){

//        String destination = searchService.getNow();
//        Date date = searchService.getToday();
        String destination = searchService.getWhere();
        Date date = searchService.getDate();

        String shape = bedroom + "室" + livingroom +"厅"+ bathroom +"卫";

        ArrayList<House> result = orderService.searchByShape(shape,destination,date);

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




}
