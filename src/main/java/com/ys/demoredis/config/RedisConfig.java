package com.ys.demoredis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
@EnableCaching
public class RedisConfig
{
    @Bean
    public RedisTemplate<String,String> getRedisTemple(RedisConnectionFactory factory)
    {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    public KeyGenerator simpleKeyGenerator()
    {
        return (o,method,objects)->{

            StringBuilder stringBuilder=new StringBuilder();

            stringBuilder.append(o.getClass().getSimpleName());
            stringBuilder.append(".");
            stringBuilder.append(method.getName());
            stringBuilder.append("[");
            for(Object object:objects)
            {
                stringBuilder.append(objects.toString());
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        };
    }
}
