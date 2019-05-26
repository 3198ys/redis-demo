package com.ys.demoredis.service;


import com.ys.demoredis.RedisService;
import com.ys.demoredis.domain.ScoreFlow;
import com.ys.demoredis.domain.User;
import com.ys.demoredis.domain.UserScore;
import com.ys.demoredis.mapper.ScoreFlowMapper;
import com.ys.demoredis.mapper.UserScoreMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RankService implements InitializingBean {
    private static final String RANKGNAME="user_score";


    @Autowired
    private RedisService redisService;


    @Autowired
    private UserService userService;

    @Resource
    private UserScoreMapper userScoreMapper;

    @Resource
    private ScoreFlowMapper scoreFlowMapper;

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

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Transactional
    public void increUserScore(String uid,Integer score)
    {
        User user = userService.getUser(Integer.valueOf(uid));
        if(user==null)
        {
            return ;
        }
        String name=user.getUserName();
        String key=uid+":"+name;
        Long score1=Long.parseLong(score+"");
        userScoreMapper.insertUserScore(new UserScore(Integer.valueOf(uid),score1,name));
        scoreFlowMapper.insertScoreFlow(new ScoreFlow(score1,uid,name));
        redisService.incrementScore(RANKGNAME,key,score1);

    }

    public Map<String,Object> userRank(String uid,String name)
    {
        Map<String,Object> resultMap=new LinkedHashMap<>();
        String key=uid+":"+name;
        Long aLong = redisService.zRank(RANKGNAME, key);
        long value = redisService.zSetScore(RANKGNAME, key).longValue();
        resultMap.put("uid",uid);
        resultMap.put("name",name);
        resultMap.put("rank",aLong);
        resultMap.put("score",value);
        return resultMap;
    }

    public List<Map<String,Object>> reverseZRankWithRank(long start,long end)
    {
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisService.reverseZRankWithRank(RANKGNAME, start, end);
        List<Map<String, Object>> collect = typedTuples.stream().map(typedtuples -> {
            Map<String, Object> map = new LinkedHashMap<>();

            map.put("userId", typedtuples.getValue().toString().split(":")[0]);
            map.put("userName", typedtuples.getValue().toString().split(":")[1]);
            map.put("score", typedtuples.getScore().toString());
            return map;
        }).collect(Collectors.toList());
        return collect;
    }

    public List<Map<String,Object>> saleRankWithScore(long start,long end)
    {
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisService.reverseZRankWithScore(RANKGNAME, start, end);
        List<Map<String, Object>> collect = tuples.stream().map(typetuple -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("userId", typetuple.getValue().toString().split(":")[0]);
            map.put("userName", typetuple.getValue().toString().split(":")[1]);
            map.put("score", typetuple.getScore().toString());
            return map;
        }).collect(Collectors.toList());
        return collect;
    }
}
