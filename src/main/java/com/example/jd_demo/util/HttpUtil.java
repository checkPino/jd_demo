package com.example.jd_demo.util;

import net.sf.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.UnsupportedEncodingException;



public class HttpUtil {


    //新增
    public static JSONObject PostRequest(RestTemplate restTemplate,String url,String tokens,String content) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Authorization",tokens);
        HttpEntity httpEntity = new HttpEntity(content,headers);
        ResponseEntity<String> res = restTemplate.postForEntity(url, httpEntity, String.class);
        return JSONObject.fromObject(res.getBody());
    }

    //获取token
    public static JSONObject GetRequest(RestTemplate restTemplate,String url) throws UnsupportedEncodingException {
        ResponseEntity<String> res = restTemplate.getForEntity(url,String.class);
        return JSONObject.fromObject(res.getBody());
    }

    public static JSONObject PutRequest(RestTemplate restTemplate,String url,String tokens,String content) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Authorization",tokens);
        HttpEntity httpEntity = new HttpEntity(content,headers);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.PUT,httpEntity,String.class);
        return JSONObject.fromObject(res.getBody());
    }



    //获取所有ck
    public static JSONObject findAll(RestTemplate restTemplate,String url,String tokens) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Authorization",tokens);
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        return JSONObject.fromObject(res.getBody());
    }

}
