package com.miaoshaproject;


import ch.qos.logback.classic.selector.servlet.LoggerContextFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void testLogger() {
        String name = "lx";
        String pwd = "123456";
        logger.debug("debug");
        logger.info("name:{} , pwd:{}",name,pwd);// 拼接的另一种写法
        logger.error("error");
    }

}
