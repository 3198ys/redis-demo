package com.ys.demoredis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.redis.serializer.RedisSerializationContext.*;


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

    @Bean
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


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory)
    {
        return new RedisCacheManager(RedisCacheWriter.lockingRedisCacheWriter(connectionFactory),this.getRedisCacheConfigtationWithTtl(600),this.getRedisCacheConfigurationMap());

    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap()
    {
        Map<String, RedisCacheConfiguration> redisConfigurationMap=new HashMap<>();
        redisConfigurationMap.put("UserInfoList",this.getRedisCacheConfigtationWithTtl(100));
        redisConfigurationMap.put("UserInfoListAnother",this.getRedisCacheConfigtationWithTtl(18000));
        return redisConfigurationMap;
    }

    /**
     * 为每一个redis的key设置时间用的工具类
     * @param seconds
     * @return
     */
    private RedisCacheConfiguration getRedisCacheConfigtationWithTtl(Integer seconds)
    {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper mapper=new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }
}
