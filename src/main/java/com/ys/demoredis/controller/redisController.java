package com.ys.demoredis.controller;

import com.ys.demoredis.RedisService;
import com.ys.demoredis.domain.User;
import com.ys.demoredis.mapper.UserMapper;
import com.ys.demoredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
user.setId("176");
        user.setUserName(name);
        userService.insertUser(null);
        return "success";
    }
    @GetMapping("/get")
    @ResponseBody
    public User get(@RequestParam("id") String id)
    {
        User user = (User)redisService.get(key + id);


        if(user==null)
        {
            User usersb= userMapper.find(id);
            System.out.println("从redis从mysql中取出");
            if(usersb!=null)
            {
                redisService.set(key+id,usersb);


                return usersb;
            }

        }
        System.out.println("从redis中取出");
        return user;
    }


}
