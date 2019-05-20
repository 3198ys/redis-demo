package com.ys.demoredis.service;

import com.ys.demoredis.domain.User;
import com.ys.demoredis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@CacheConfig(cacheNames = "userInfoCache")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @CachePut(key="#p0.id")
    public User insertUser(User u) {
        Assert.notNull(u,"不能为空");
        userMapper.insert(u);
        return userMapper.find(u.getId());
    }
}
