package com.ys.demoredis.service;


import com.ys.demoredis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RankService {
    private static final String RANKGNAME="user_score";


    @Autowired
    private RedisService redisService;

    /**
     * 排行榜添加字段
     * @param uid
     * @param score
     */
    public void rankAdd(String uid,Integer score)
    {
        redisService.zAdd(RANKGNAME,uid,score);
    }

    public void increScore(String uid,Integer score)
    {
        redisService.incrementScore(RANKGNAME,uid,score);

    }

    /**
     * 返回对应的value的排名
     * @param uid
     * @return
     */
    public Long rankNum(String uid)
    {
        return redisService.zRank(RANKGNAME,uid);
    }

    /**
     * 返回对应的score
     * @param uid
     * @return
     */
    public Long score(String uid)
    {
        return redisService.zSetScore(RANKGNAME,uid).longValue();
    }

    /**
     * 返回分数和对应的key
     *
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> rankWithScore(Integer start, Integer end)
    {
     return redisService.zRankWithScore(RANKGNAME,start,end);
    }
}
