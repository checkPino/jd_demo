package com.example.jd_demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.jd_demo.entity.QL;
import com.example.jd_demo.entity.Servers;
import com.example.jd_demo.service.QLService;
import com.example.jd_demo.service.SServers;
import com.example.jd_demo.util.HttpUtil;
import com.example.jd_demo.util.R;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class QLServiceImpl implements QLService {

    @Autowired
    private RestTemplate myRestTemplate;

    @Autowired
    private SServers sServers;

    private String tks = "";

    private Boolean b = true;

    @Override
    public R findAll(String value) {
        List<QL> list = new ArrayList<>();
        String searchValue = "";
        if (!"all".equals(value)) {
            searchValue = value;
        }

        String url = "http://192.168.123.17:5800/open/envs?searchValue=" + searchValue;
        //String token = "Bearer 5a5e9c21-1934-4f98-8901-2ac2ce921f11";

        try {
            JSONObject res = HttpUtil.findAll(myRestTemplate, url, tks);
            if ("401".indexOf(res.getString("code")) != -1) {

                if (b) {
                    //模拟 重新获取token  并保存到redis
                    tks = "Bearer 5a5e9c21-1934-4f98-8901-2ac2ce921f1";
                    b = false;
                    return findAll(searchValue);
                }
                b = true;
                return R.error();

            }
            b = true;
            String data = res.getString("data");
            data = data.substring(1, data.length() - 1);
            if (!data.contains("{")) {
                return R.error().message("没有ck，查鸡毛");
            }
            String[] split = data.split("},");

            //分割后 除了最后一个 其他都少  ‘}’
            for (int i = 0; i < split.length; i++) {
                if (i != split.length - 1) {
                    split[i] = split[i] + "}";
                }
                QL ql = new QL();
                ql.setRemarks(JSON.parseObject(split[i]).getString("remarks"));
                ql.setValue(JSON.parseObject(split[i]).getString("value"));
                ql.set_id(JSON.parseObject(split[i]).getString("_id"));
                ql.setCreated(JSON.parseObject(split[i]).getString("created"));
                ql.setStatus(JSON.parseObject(split[i]).getString("status"));
                ql.setTimestamp(JSON.parseObject(split[i]).getString("timestamp"));
                ql.setPosition(JSON.parseObject(split[i]).getString("position"));
                ql.setName(JSON.parseObject(split[i]).getString("name"));
                list.add(ql);
            }
            return R.ok().data("ck", list);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return R.error().message("查询失败");
    }


    /***
     * 添加ck
     * @param ql
     * @param servers
     * @return
     */
    @Override
    public R addCK(QL ql, Servers servers) {
        //从redis 拿到某一个服务器的信息
        servers = (Servers) sServers.getObj(servers.getRedisKey()).getData().get("data");

        String url = servers.getIp() + "/open/envs";
        String token = servers.getToken();

        try {
            //添加ck
            JSONObject res = HttpUtil.PostRequest(myRestTemplate, url, token, ql.add());
            b = true;
            return R.ok().data("ck", res.getString("data"));
        } catch (Exception e) {
            e.printStackTrace();
            //重新获取token后还是失败就结束
            if (b) {
                System.out.println("------------------------------------------------------------");
                b = false;
                String u = servers.getIp() + "/open/auth/token?client_id=" + servers.getClientID() + "&client_secret=" + servers.getClientSecret();
                JSONObject jsonObject = null;
                try {
                    jsonObject = HttpUtil.GetRequest(myRestTemplate, u);
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    unsupportedEncodingException.printStackTrace();
                }
                if ("400".indexOf(jsonObject.getString("code")) != -1) {
                    return R.error().message(jsonObject.getString("message"));
                }
                String data = jsonObject.getString("data");
                String tk = JSON.parseObject(data).getString("token");
                servers.setToken("Bearer " + tk);
                //更新redis 里的数据
                sServers.set(servers.getRedisKey(), servers);
                return addCK(ql, servers);
            }
            b = true;
            return R.error();
        }

    }
}
