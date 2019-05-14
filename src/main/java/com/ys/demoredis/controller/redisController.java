package com.ys.demoredis.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/test")
public class redisController {
    @Resource
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/getRedis",method = RequestMethod.GET)
    @ResponseBody
    public String getValue(String name)
    {
        redisTemplate.opsForValue().set("name",name);
        System.out.println("存入chenggong");
        String name1 = (String)redisTemplate.opsForValue().get("name");
        return name1;
    }
}
