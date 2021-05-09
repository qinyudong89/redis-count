package com.example.rediscount.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class RedisUiltTest {

    @Autowired
    private RedisUilt redisUilt;

    @Test
    public void incr(){
        redisUilt.incr("my");
        log.info("my:{}", redisUilt.get("my"));
    }
    @Test
    public void sadd(){
        for (int i = 2000; i <5000 ; i++) {
            int count = 1000+i;
            String key = count+"";
            log.info("key:{},value:{}", key, i);
            redisUilt.sadd(key,i);
        }
    }
}