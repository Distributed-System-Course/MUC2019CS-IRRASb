package com.example.houseSearch.mapper;

import com.example.houseSearch.bean.House;
import com.sun.media.jfxmediaimpl.HostUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.sql.Date;
import java.util.ArrayList;

@Mapper
public interface orderMapper {


    ArrayList<House> getOrderByPriceBig(@Param("today") Date today, @Param("destination") String destination);

    ArrayList<House> getOrderByPriceSmall(@Param("today") Date today, @Param("destination") String destination);

    ArrayList<House> getOrderByDistanceBig( @Param("today")  Date today, @Param("destination")  String destination  );

    ArrayList<House> getOrderByDistanceSmall( @Param("today")  Date today, @Param("destination")  String destination  );

    ArrayList<House> getOrderBySizeSmall( @Param("today")  Date today, @Param("destination")  String destination  );

    ArrayList<House> getOrderBySizeBig( @Param("today")  Date today, @Param("destination")  String destination  );

  //  ArrayList<House> getSearchByShape(@Param("shape") String shape ,@Param("today") Date today,@Param("destination") String destination);

  //  ArrayList<House> getSearchByPrice(@Param("price") int price, @Param("today") Date today, @Param("destination")  String destination );
}
