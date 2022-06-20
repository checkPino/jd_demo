package com.example.jd_demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.jd_demo.entity.QL;
import com.example.jd_demo.entity.Servers;
import com.example.jd_demo.service.QLService;
import com.example.jd_demo.util.HttpUtil;
import com.example.jd_demo.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Api(tags = "青龙ck操作")
@RestController
@RequestMapping("/ql")
public class QLController {

    @Autowired
    private QLService qlService;


    @ApiOperation(value = "查询所有ck")
    @GetMapping("/findAll/{searchValue}")
    public R findAll(@PathVariable("searchValue") String value) {

        return qlService.findAll(value);

    }


    /***
     * map  接收两个对象  1个是服务器对象  1个是ck具体信息
     * @param map
     * @return
     */
    @ApiOperation(value = "添加ck")
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

        return qlService.addCK(ql,servers);

    }

    @ApiOperation(value = "更新ck")
    @PostMapping("/put")
    public R putCK(@RequestBody QL ql) {
        /*String url = "http://192.168.123.17:5800/open/envs";
        String token = "Bearer 5a5e9c21-1934-4f98-8901-2ac2ce921f11";
        try {
            JSONObject res = HttpUtil.PutRequest(url, token, ql.add().substring(1, ql.add().length() - 1));

            if (!"200".equals(res.getString("code"))) {
                return R.error().message("添加失败");
            }
            return R.ok().data("ck", res.getString("data"));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message("添加失败");
        }*/

        return null;
    }


}
