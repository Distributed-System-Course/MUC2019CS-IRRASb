package com.example.houseSearch.service;

// 业务接口

import java.sql.Date;

public interface searchService {

    void search(String where);

    String getWhere();

    Date getDate();

}
