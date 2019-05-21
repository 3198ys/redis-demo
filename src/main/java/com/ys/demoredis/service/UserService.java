package com.ys.demoredis.service;

import com.ys.demoredis.domain.User;
import com.ys.demoredis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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

    @CachePut(value = "UserInfoList",keyGenerator = "simpleKeyGenerator")
    public User insertUser(User u) {
        Assert.notNull(u,"不能为空");
        userMapper.insert(u);
        return userMapper.find(u.getId());
    }

    @CacheEvict("#p0")
    public void delete(int id)
    {
        userMapper.delete(id);
    }

    //清空缓存为userInfoCache的所有缓存
    @CacheEvict(allEntries = true)
    public void deleteAll()
    {
        userMapper.deleteAll();
    }

    @Cacheable(value = "UserInfoList",keyGenerator = "simpleKeyGenerator")
    public User getUser(int id)
    {
        User user = userMapper.find(id);
        return user;
    }
}
