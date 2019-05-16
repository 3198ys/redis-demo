package com.ys.demoredis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    private static double size = Math.pow(2, 32);


    /**
     * 写入缓存
     *
     * @param key
     * @param offset
     * @param isShow
     * @return
     */
    public boolean setBit(String key, long offset, boolean isShow) {
        boolean result = false;

        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.setBit(key, offset, isShow);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param offset
     * @return
     */
    public boolean getBit(String key, long offset) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.getBit(key, offset);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();

            operations.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean set(final String key, Object value, long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 判断是否存在key
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, hashKey, value);
    }

    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, hashKey);
    }

    public void lpush(String k, Object v) {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        listOperations.leftPush(k, v);
    }

    public List<Object> getRange(String key, long l, long l1) {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        return listOperations.range(key, l, l1);

    }

    public void sadd(String key, Object value) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        setOperations.add(key, value);
    }

    public Set<Object> getMembers(String key) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        return setOperations.members(key);

    }
      public void zadd(String key,Object value,double score)
      {
          ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();
          zSetOperations.add(key,value,score);
      }
      public Set<Object> rangeByScore(String key,double score,double score1)
      {
          ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();
          return zSetOperations.rangeByScore(key,score,score1);
      }

      public void saveDataToRedis(String name)
      {
          double index = Math.abs(name.hashCode() % size);
          long indexLong = new Double(index).longValue();
          boolean availableUsers = setBit("availableUsers", indexLong, true);
      }

      public boolean getDataToRedis(String name)
      {
          double index = Math.abs(name.hashCode() % size);
          long indexLong = new Double(index).longValue();
          return getBit("availableUsers",indexLong);
      }

      public Long zRank(String key,Object value)
      {
          ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();
          return zSetOperations.rank(key,value);
      }

      public Set<ZSetOperations.TypedTuple<Object>> zRankWithScore(String key,long start,long end)
      {
          ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();

          return zSetOperations.rangeWithScores(key,start,end);
      }
      public Double zSetScore(String key,Object value)
      {
          ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();
          return zSetOperations.score(key,value);
      }
      public void incrementScore(String key,Object value,double scoure)
      {
          ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();
          zSetOperations.incrementScore(key,value,scoure);
      }

      public Set<ZSetOperations.TypedTuple<Object>> reverseZRankWithScore(String key,long start,long end)
      {
          ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();
          return zSetOperations.reverseRangeByScoreWithScores(key,start,end);
      }
      public Set<ZSetOperations.TypedTuple<Object>> reverseZrankWithRank(String key,long start,long end)
      {
          ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();
          return zSetOperations.reverseRangeWithScores(key,start,end);
      }

}
