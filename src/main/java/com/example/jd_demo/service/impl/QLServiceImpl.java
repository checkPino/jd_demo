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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QLServiceImpl implements QLService {

    @Autowired
    private RestTemplate myRestTemplate;

    @Autowired
    private SServers sServers;

    private Boolean b = true;


    /***
     * 根据条件查询
     * @param value   条件
     * @param server  哪个服务器
     * @return
     */
    @Override
    public R findAll(String value, String server) {

        List<QL> list = new ArrayList<>();
        String searchValue = "";
        if (!"all".equals(value)) {
            searchValue = value;
        }

        //从redis 获取 服务器信息
        Servers servers = (Servers) sServers.getObj(server).getData().get("data");

        String url = servers.getIp() + "/open/envs?searchValue=" + searchValue;
        String token = servers.getToken();

        try {
            JSONObject res = HttpUtil.findAll(myRestTemplate, url, token);
            if ("401".indexOf(res.getString("code")) != -1) {

                //重新获取token后还是失败就结束
                if (b) {
                    b = false;
                    R r = updataServer(servers);
                    if (r.getCode() != 20000) {
                        return R.error().message(r.getMessage());
                    }
                    servers = (Servers) r.getData().get("data");
                    return findAll(value, server);
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
            return R.ok().data("data", list);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return R.error().message("查询失败");
    }


    /***
     * token错误时调用 从新获取token并 更新redis
     * @param servers
     * @return
     */
    @Override
    public R updataServer(Servers servers) {
        String u = servers.getIp() + "/open/auth/token?client_id=" + servers.getClientID() + "&client_secret=" + servers.getClientSecret();
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpUtil.GetRequest(myRestTemplate, u);

            if ("400".indexOf(jsonObject.getString("code")) != -1) {
                return R.error().message(jsonObject.getString("message"));
            }
            String data = jsonObject.getString("data");
            String tk = JSON.parseObject(data).getString("token");
            servers.setToken("Bearer " + tk);
            //更新redis 里的数据
            sServers.set(servers.getRedisKey(), servers);
            Map map = new HashMap<>();
            map.put("data", servers);
            return R.ok().data(map);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return R.error();
        }

    }


    @Override
    public R updataCK(QL ql, Servers servers) {

        //从redis 拿到某一个服务器的信息
        servers = (Servers) sServers.getObj(servers.getRedisKey()).getData().get("data");

        String url = servers.getIp() + "/open/envs";
        String token = servers.getToken();
        String content = ql.add().substring(1, ql.add().length() - 1);
        try {
            JSONObject res = HttpUtil.PutRequest(myRestTemplate,url, token,content );
            if (!"200".equals(res.getString("code"))) {
                return R.error().message("更新失败");
            }
            return R.ok().data("ck", res.getString("data"));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message("异常了--更新失败");
        }

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
                b = false;
                R r = updataServer(servers);
                if (r.getCode() != 20000) {
                    return R.error().message(r.getMessage());
                }
                servers = (Servers) r.getData().get("data");
                return addCK(ql, servers);
            }
            b = true;
            return R.error();
        }


    }
}
