package com.example.jd_demo.service;


import com.example.jd_demo.entity.QL;
import com.example.jd_demo.entity.Servers;
import com.example.jd_demo.util.R;

public interface QLService {
    //按条件查询ck
    R findAll(String value);

    //添加ck
    R addCK (QL ql, Servers servers);

}
