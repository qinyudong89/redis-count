package com.example.rediscount.utils;

import com.example.rediscount.service.DelBigKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Map;

@Slf4j
@SpringBootTest
class RedisUiltTest {

    @Autowired
    private RedisUilt redisUilt;
    @Autowired
    private DelBigKey delBigKey;


    @Test
    public void incr() {
        redisUilt.incr("my");
        log.info("my:{}", redisUilt.get("my"));
    }

    @Test
    public void sadd() {
        for (int i = 1; i < 500; i++) {
            String key = "skey";
            log.info("key:{},value:{}", key, i);
            redisUilt.sadd(key, i);
        }
    }

    @Test
    public void hset() {
        for (int i = 1; i < 500; i++) {
            String key = "big:hash";
            log.info("key:{},value:{}", key, i, i);
            redisUilt.hset(key, i + "", i);
        }
    }

    //分页
    @Test
    public void hscan() {
        //每页最条数
        int pageSize = 20;
        String pattern = "2*";
        ScanOptions scanOptions = new ScanOptions.ScanOptionsBuilder().count(pageSize).match(pattern).build();
        Cursor<Map.Entry<Object, Object>> curosr = redisUilt.hscan("big:hash", scanOptions);

        for (int i = 0; i < pageSize; i++) {
            if (!curosr.hasNext()) {
                break;
            }
            Map.Entry<Object, Object> entry = curosr.next();
            log.info("结果|position:{},cursorId:{},key:{},val:{}", curosr.getPosition(), curosr.getCursorId(), entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void delHashBigkey(){
        delBigKey.delBigHash("big:hash");
    }
    @Test
    public void setDelBigKey(){
        delBigKey.delBigSet("skey");
    }

    @Test
    public void lpush(){
        for (int i = 0; i < 500; i++) {
            redisUilt.lpush("big:list",i);
        }
    }
    @Test
    public void delBigList(){
        delBigKey.delBigList("big:list");
    }

    @Test
    public void zadd(){
        for (int i = 0; i < 500; i++) {
            redisUilt.zAdd("big:zset",i,i);
        }
    }

    @Test
    public void delBigZset(){
        delBigKey.delBigZset("big:zset");
    }
}
