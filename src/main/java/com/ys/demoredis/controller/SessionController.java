package com.ys.demoredis.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping("/set")
    @ResponseBody
    public Map<String,Object> setSession(HttpServletRequest request)
    {
        request.getSession().setAttribute("request url",request.getRequestURL().toString());
        request.getSession().setAttribute("ys","ysys");
        Map<String,Object> map=new HashMap<>();
        map.put("ys",request.getSession().getAttribute("ys"));
        map.put("url",request.getSession().getAttribute("request url"));
        map.put("sessionId",request.getSession().getId());

        return map;
    }
    @RequestMapping("/get")
    @ResponseBody
    public Map<String,Object> getSession(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Map<String,Object> map=new HashMap<>();

        map.put("sessionId",session.getId());
        map.put("ys",request.getSession().getAttribute("ys"));
        return map;

    }
}
