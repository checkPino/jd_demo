package com.example.jd_demo.service.impl;

import com.example.jd_demo.entity.Servers;
import com.example.jd_demo.service.SServers;
import com.example.jd_demo.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SServersImpl implements SServers {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /***
     * 查询 对象
     * @return
     */
    @Override
    public R getObj(String key) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Servers.class));
        Object o = redisTemplate.opsForValue().get(key);
        Map map = new HashMap();
        map.put("data",o);
        return R.ok().data(map);
    }

    /***
     * 返回字符串
     * @param key
     * @return
     */
    @Override
    public String getStr(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /***
     * 更新一个 对象
     */
    @Override
    public void set(String key,Object o) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Servers.class));
        redisTemplate.opsForValue().set(key,o);
    }

    /***
     * 更新一个字符串
     * @param key
     * @param value
     */
    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }
}
