package com.example.jd_demo.controller;

import com.example.jd_demo.entity.Servers;
import com.example.jd_demo.service.SServers;
import com.example.jd_demo.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servers")
public class ServersController {

    @Autowired
    private SServers sServers;


    @PostMapping("/updata")
    public R updatObj(@RequestBody Servers servers){
        sServers.set(servers.getRedisKey(),servers);
        return R.ok();
    }
}
