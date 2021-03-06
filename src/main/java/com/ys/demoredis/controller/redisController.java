package com.ys.demoredis.controller;

import com.ys.demoredis.RedisService;
import com.ys.demoredis.domain.User;
import com.ys.demoredis.mapper.UserMapper;
import com.ys.demoredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/test")
public class redisController {


    private static final String key = "userCache_";
    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;


    @Autowired
    private UserService userService;


    @Autowired
    private JedisPool jedisPool;

    @RequestMapping(value = "/getRedis", method = RequestMethod.GET)
    @ResponseBody
    public String getValue(String name) {
        redisTemplate.opsForValue().set("name", name);
        System.out.println("存入chenggong");
        String name1 = (String) redisTemplate.opsForValue().get("name");
        return name1;
    }

    @GetMapping("/add")
    @ResponseBody
    public String add(@RequestParam("name") String name) {
        User user = new User();

        user.setUserName(name);
        userService.insertUser(user);
        return "success";
    }
    @GetMapping("/get")
    @ResponseBody
    public User get(@RequestParam("id") int id)
    {
        User user = userService.getUser(id);
        return user;
    }

    @GetMapping("/getName")
    @ResponseBody
    public List<User> getUser(@RequestParam("userName") String userName)
    {
        return userService.getUserWithName(userName);
    }

    @GetMapping("/testredis/{text}")
    public void setString(@PathVariable("text") String text)
    {
        Jedis resource = jedisPool.getResource();
        Transaction multi = resource.multi();
        multi.lpush("mylist2","1");
        multi.lpush("mylist2","2");


        multi.lpush("mylist2","3");
        multi.lpush("mylist2","4");
        multi.exec();
    }


}
