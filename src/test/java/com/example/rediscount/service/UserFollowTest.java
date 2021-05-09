package com.example.rediscount.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserFollowTest {
    @Autowired
    private UserFollow userFollow;

   /* @Test
    void follow() {
        userFollow.follow(1001L, 4467L);
    }
    @Test
    void cancelFollow() {
        userFollow.cancelFollow(1001L, 3001L);
    }

    @Test
    void commonlFollow() {
        Set<Long> otherKeys = new HashSet< >();
        otherKeys.add(1002L);
        userFollow.commonFollow(1001L, otherKeys, "newFollow");
    }*/
}