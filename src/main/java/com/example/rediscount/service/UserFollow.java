package com.example.rediscount.service;

import com.example.rediscount.utils.RedisUilt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserFollow {
    @Autowired
    private RedisUilt redisUilt;

    public void follow(Long uid, Long followId){
        //将对方id添加到自己的关注列表中
        redisUilt.zAdd("follow:"+uid, followId, System.currentTimeMillis());
        //将自己的id添加到对方的粉丝列表中
        redisUilt.zAdd("fans:"+followId, uid, System.currentTimeMillis());
    }
    public void cancelFollow(Long uid, Long followId){
        //将对方id添加到自己的关注列表中
        redisUilt.zRemove("follow:"+uid, followId);
        //将自己的id添加到对方的粉丝列表中
        redisUilt.zRemove("fans:"+followId, uid);
    }

    /**
     *  求共同关注
     * @param uid
     * @param otherKeys
     * @param destKey 共同关注保存到新的KEY
     */
    public void commonFollow(Long uid, Collection<Long> otherKeys, String destKey){
        Set<String> otherSet = new HashSet<>();
        for (Long otherId: otherKeys ) {
            otherSet.add("follow:"+otherId);
        }
        redisUilt.intersectAndStore("follow:"+uid, otherSet, destKey);
    }
}
