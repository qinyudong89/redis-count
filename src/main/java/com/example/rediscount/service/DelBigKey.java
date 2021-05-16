package com.example.rediscount.service;

import com.alibaba.fastjson.JSON;
import com.example.rediscount.utils.RedisUilt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 通过scan分批删除bigkey
 */
@Slf4j
@Component
public class DelBigKey {

    @Autowired
    private RedisUilt redisUilt;

    public void delBigList(String bigListKey) {
        long size = redisUilt.llen(bigListKey);
        int left = 100;
        int counter = 0;
        while (counter < size){
            log.info("bigListKey:{}, start:{},count:{}",bigListKey,counter,left);
            redisUilt.lrem(bigListKey, counter, left);
            counter += left;
        }
        redisUilt.del(bigListKey);
    }

    public void delBigHash(String bigHashKey) {
        int count = 100;
        ScanOptions scanOptions = new ScanOptions.ScanOptionsBuilder().count(count).build();
        Cursor<Map.Entry<Object, Object>> curosr = redisUilt.hscan("big:hash", scanOptions);
        do {
            Map.Entry<Object, Object> entry = curosr.next();
            String field = (String) entry.getKey();
            log.error("结果|position:{},key:{}", curosr.getPosition(), field);
            redisUilt.hdel(bigHashKey, field);
        } while (curosr.hasNext());
        redisUilt.del(bigHashKey);
    }

    public void delBigSet(String bigSetKey) {
        int count = 100;
        ScanOptions scanOptions = new ScanOptions.ScanOptionsBuilder().count(count).build();
        Cursor<Object> curosr = redisUilt.sscan(bigSetKey, scanOptions);
        do {
            if (curosr.hasNext()){
                redisUilt.sremove(bigSetKey,curosr.next());
            }
        } while (curosr.hasNext());
        redisUilt.del(bigSetKey);
    }

    public void delBigZset(String bigZsetKey){
        int count = 100;
        ScanOptions scanOptions = new ScanOptions.ScanOptionsBuilder().count(count).build();
        Cursor<ZSetOperations.TypedTuple<Object>> curosr = redisUilt.zScan(bigZsetKey, scanOptions);
        do {
           if (curosr.hasNext()){
               ZSetOperations.TypedTuple<Object> typedTuple = curosr.next();
               log.info("zset:{}", JSON.toJSONString(typedTuple));
               redisUilt.zRemove(bigZsetKey, typedTuple.getValue());
           }
        }while (curosr.hasNext());
        redisUilt.del(bigZsetKey);
    }
}
