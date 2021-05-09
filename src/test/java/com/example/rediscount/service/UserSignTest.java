package com.example.rediscount.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class UserSignTest {

    @Autowired
    private UserSign userSign;
    @Test
    void doSign() {
        userSign.doSign(1001L, LocalDate.of(2021,4,3));
    }

    @Test
    void checkSign() {
        boolean isSign = userSign.checkSign(1001L,LocalDate.of(2021,1,2));
        log.info("是否签到：{}",isSign);
    }

    @Test
    void getSignCount() {
        long count = userSign.getSignCount(1001L, LocalDate.of(2021,1,2));
        log.info("签到字数：{}", count);
    }

    @Test
    void getContinuousSignCount() {
        long count = userSign.getContinuousSignCount(1001L, LocalDate.of(2021,4, 4));
        log.info("连续签到次数：{}", count);
    }

    @Test
    void getFirstSignDate() {
        LocalDate date = userSign.getFirstSignDate(1001L, LocalDate.now());
        log.info("当月第一次签到日期：{}", date);
    }

    @Test
    void getSignInfo() {
        Map<String, Boolean> userSignMap = userSign.getSignInfo(1001L, LocalDate.now());
        for (Map.Entry<String,Boolean> entry: userSignMap.entrySet()) {
            log.info(entry.getKey()+", 是否签到：{}", entry.getValue());
        }
    }
}