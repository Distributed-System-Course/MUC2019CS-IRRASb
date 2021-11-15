package com.example.houseSearch.mapper;

import com.example.houseSearch.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface selectUserMapper {

    ArrayList<User> getAllUser();

}
