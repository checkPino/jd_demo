package com.example.jd_demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.jd_demo.entity.QL;
import com.example.jd_demo.entity.Servers;
import com.example.jd_demo.service.QLService;
import com.example.jd_demo.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "青龙ck操作")
@RestController
@RequestMapping("/ql")
public class QLController {

    @Autowired
    private QLService qlService;


    @ApiOperation(value = "查询所有ck")
    @GetMapping("/findAll/{searchValue}/{server}")
    public R findAll(@PathVariable("searchValue") String value ,@PathVariable("server") String server) {

        return qlService.findAll(value,server);

    }


    /***
     * map  接收两个对象  1个是服务器对象  1个是ck具体信息
     * @param map
     * @return
     */
    @ApiOperation(value = "添加ck/更新")
    @PostMapping("/add")
    public R addCK(@RequestBody Map<String, Object> map) {

        QL ql = new QL();
        Servers servers = new Servers();
        if (!StringUtils.isEmpty(map.get("ql"))) {
            ql = JSON.parseObject(JSON.toJSONString(map.get("ql")), QL.class);
        }
        if (!StringUtils.isEmpty(map.get("servers"))) {
            servers = JSON.parseObject(JSON.toJSONString(map.get("servers")), Servers.class);
        }

        if (ql.getValue().indexOf("pt_pin") != -1) {
            String pt_pin = ql.getValue().substring(ql.getValue().indexOf("pt_pin"), ql.getValue().length());
            R r = qlService.findAll(pt_pin,servers.getRedisKey());
            //20000 是查到数据 就走更新
            if (r.getCode() == 20000){
                List<QL> data = (List<QL>) r.getData().get("data");
                ql.set_id(data.get(0).get_id());
                ql.setName(data.get(0).getName());
                return qlService.updataCK(ql,servers);
            }
        }

        return qlService.addCK(ql,servers);

    }

}
