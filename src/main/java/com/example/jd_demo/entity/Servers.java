package com.example.jd_demo.entity;


import java.io.Serializable;

public class Servers implements Serializable {

    private String redisKey;
    private String ip;
    private String token;
    private String remarks;
    private String clientID;
    private String clientSecret;

    public Servers() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public String toString() {
        return "Servers{" +
                "redisKey='" + redisKey + '\'' +
                ", ip='" + ip + '\'' +
                ", token='" + token + '\'' +
                ", remarks='" + remarks + '\'' +
                ", clientID='" + clientID + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }
}
