package com.hzyw;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableSwagger2Doc
@EnableScheduling
@MapperScan({"com.hzyw.basic.dao.mapper"})
public class TokenApplication {
    public static void main(String... args) {
        SpringApplication.run(TokenApplication.class, args);
    }
}

