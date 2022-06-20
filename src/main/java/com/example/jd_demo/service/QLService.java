package com.example.jd_demo.service;


import com.example.jd_demo.entity.QL;
import com.example.jd_demo.entity.Servers;
import com.example.jd_demo.util.R;

public interface QLService {
    //按条件查询ck
    R findAll(String value , String server);

    //添加ck
    R addCK (QL ql, Servers servers);

    //更新ck
    R updataCK(QL ql, Servers servers);

    //token错误时调用 从新获取token并 更新redis
    R updataServer(Servers servers);

}
