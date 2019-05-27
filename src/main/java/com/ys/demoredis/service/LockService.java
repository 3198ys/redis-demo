package com.ys.demoredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class LockService {
    public final static long DEFAULT_LOCK_TIMEOUT = 10L * 1000;
    public static final long DEFAULT_ACQUIRE_RESOLUTION_MILLIS = 100L;

    @Autowired
    private StringRedisTemplate redisTemplate;


    public UUID acquire(String lockKey, long acquireTimeOutInMills, long lockExpireInMills) throws InterruptedException {

        UUID uuid = UUID.randomUUID();
        long timeout = 0L;
        while (timeout < acquireTimeOutInMills) {
            if (redisTemplate.opsForValue().setIfAbsent(lockKey, uuid.toString())) {

                redisTemplate.expire(lockKey,lockExpireInMills, TimeUnit.MILLISECONDS);
                return uuid;
            }
            TimeUnit.MILLISECONDS.sleep(DEFAULT_ACQUIRE_RESOLUTION_MILLIS);
            timeout+=DEFAULT_ACQUIRE_RESOLUTION_MILLIS;
        }
        return null;
    }
    public void release(String lockey,UUID uuid)
    {
        if(uuid==null)
        {
            return;
        }
        String s = redisTemplate.opsForValue().get(lockey);
        if(s.equals(uuid.toString()))
        {
            redisTemplate.delete(lockey);
        }else
        {
            throw new RuntimeException("不能释放");
        }
    }



}
