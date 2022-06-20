package com.example.jd_demo.service;


import com.example.jd_demo.util.R;

public interface SServers {

    //查询
    R getObj(String key);
    String getStr(String key);

    //更新
    void set(String key,Object o);
    void set(String key,String value);


}
