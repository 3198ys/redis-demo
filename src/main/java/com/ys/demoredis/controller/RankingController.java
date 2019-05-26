package com.ys.demoredis.controller;

import com.ys.demoredis.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class RankingController {

    @Autowired
    private RankService rankService;

    @GetMapping("/addScore")
    public String addRank(String uid,Integer score)
    {
     rankService.rankAdd(uid,score);
     return "success";
    }

    @GetMapping("/increScore")
    public String increScore(String uid,Integer score)
    {
        rankService.increScore(uid,score);
        return "success";
    }
    @GetMapping("/rank")
    public Map<String,Long> rank(String uid)
    {
        Long aLong = rankService.rankNum(uid);
        Map<String,Long> map=new HashMap<>();
        map.put(uid,aLong);
        return map;
    }
    @GetMapping("/scoreByRange")
    public Set<ZSetOperations.TypedTuple<Object>> scoreByRange(Integer start,Integer end)
    {
        return rankService.rankWithScore(start,end);
    }

    @GetMapping("/sale/increScore")
    @ResponseBody
    public String increSaleScore(String uid,Integer score)
    {

        rankService.increUserScore(uid,score);
        return "success";
    }

    @GetMapping("/sale/userScore")
    @ResponseBody
    public Map<String,Object> userScore(String uid,String name)
    {
        Map<String, Object> stringObjectMap = rankService.userRank(uid, name);
        return stringObjectMap;
    }
    @GetMapping("/sale/top")
    @ResponseBody
    public List<Map<String,Object>> rerverseZRankWithRank(long start,long end)
    {
        List<Map<String, Object>> maps = rankService.reverseZRankWithRank(start, end);
        return maps;
    }


    @GetMapping("/sale/scoreByRange")
    @ResponseBody
    public List<Map<String,Object>> reverseZRankWithScore(long start,long end)
    {
        List<Map<String, Object>> maps = rankService.saleRankWithScore(start, end);
        return maps;
    }
}
