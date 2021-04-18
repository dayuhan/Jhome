package com.account.modules.userAuthority.service.impl;

import com.account.modules.userAuthority.service.RedisService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhiqiang.liu1
 */
@Service
@Log4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate template;

    @Override
    public void remove(String... keys) {
        String[] stringKeys = keys;
        int num = keys.length;
        for (int count = 0; count < num; ++count) {
            String key = stringKeys[count];
            this.remove(key);
        }
    }

    @Override
    public String get(String key) {
        ValueOperations<String, String> operations = this.template.opsForValue();
        return operations.get(key);
    }

    @Override
    public boolean set(String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = this.template.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception var) {
            var.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean set(String key, String value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = this.template.opsForValue();
            operations.set(key, value);
            this.template.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception var) {
            var.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean expire(String key, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            this.template.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception var) {
            var.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean exists(String key) {
        return this.template.hasKey(key);
    }

    @Override
    public void remove(String key) {
        if (this.exists(key)) {
            this.template.delete(key);
        }
    }

    @Override
    public List<String> range(String key, Long start, Long end) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.range(key, start, end);
    }


    @Override
    public String listKey(String content) {
        log.info("redisServiceImpl listKey info " + content);
        return null;
    }
}
