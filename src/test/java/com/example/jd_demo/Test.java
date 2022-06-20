package com.example.jd_demo;


import com.alibaba.fastjson.JSON;
import com.example.jd_demo.entity.QL;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Test {

    RestTemplate restTemplate = new RestTemplate();

    /***
     * 获取token
     */
    @org.junit.Test
    public void findToken() {
        //RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.123.17:5800/open/auth/token?client_id=0Gb8_-ClRsks&client_secret=7FMpPQeu_6vcL_P2Gc-8Td8K";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String s = forEntity.toString().substring(5, forEntity.toString().indexOf("[") - 1);
        String data = JSON.parseObject(s).getString("data");
        System.out.println(data);
        //String token = JSON.parseObject(data).getString("token");
    }

    /***
     * 获取所有变量
     */
    @org.junit.Test
    public void findAll() {
        //RestTemplate restTemplate = new RestTemplate();
        //查询条件
        String searchValue = "";
        String url = "http://192.168.123.17:5800/open/envs?searchValue="+searchValue;
        String token = "Bearer 5a5e9c21-1934-4f98-8901-2ac2ce921f11";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, httpEntity, String.class);
        String res = responseEntity.toString().substring(5, responseEntity.toString().indexOf("Server") - 2);
        //System.out.println(res);
        String data = JSON.parseObject(res).getString("data");
        data = data.substring(1, data.length() - 1);
        System.out.println(data);
        String[] split = data.split("},");

        //分割后 除了最后一个 其他都少  ‘}’
        for (int i = 0; i < split.length; i++) {
            if (i != split.length - 1) {
                split[i] = split[i] + "}";
            }
            System.out.println(JSON.parseObject(split[i]).getString("remarks"));
        }
    }

    /***
     * 新增  变量
     */
    @org.junit.Test
    public void AddCK(){
        //RestTemplate restTemplate = new RestTemplate();
        //查询条件
        String searchValue = "";
        String url = "http://192.168.123.17:5800/open/envs";
        String token = "Bearer 5a5e9c21-1934-4f98-8901-2ac2ce921f11";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Authorization", token);
        String con = "[{\"name\":\""+"idea"+"\","
                +"\"remarks\":\""+"我是帅比"+"\","
                +"\"value\":\""+"我是帅比"+"\"}]";
        HttpEntity httpEntity = new HttpEntity(con,headers);

        HttpEntity<String> res = restTemplate.postForEntity(url, httpEntity, String.class);
        System.out.println(res.getBody());

    }

    /***
     * 根据id 修改变量
     */
    @org.junit.Test
    public void Updata(){
        String searchValue = "";
        String url = "http://192.168.123.17:5800/open/envs";
        String token = "Bearer 5a5e9c21-1934-4f98-8901-2ac2ce921f11";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Authorization", token);
        QL QL = new QL();
        QL.set_id("nmONBFMsUwkL8AO3");
        QL.setName("idea");
        QL.setRemarks("这是备注呀");
        QL.setValue("主要修改这个");
        String con = QL.add();
        System.out.println("asd"+con);
        HttpEntity httpEntity = new HttpEntity(con,headers);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
        System.out.println(res.getBody());
    }


    @org.junit.Test
    public void redis(){
        StringRedisTemplate s = new StringRedisTemplate();

        ValueOperations<String, String> ops = s.opsForValue();
        String s1 = ops.get("key");
        System.out.println(s1);
    }


}
