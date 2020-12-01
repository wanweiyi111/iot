package com.hzyw;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableSwagger2Doc
@EnableScheduling
/**
 * @author weianqi 2019.7.29
 */

public class ReportApplication {

    public static void main(String... args) {
        SpringApplication.run(ReportApplication.class, args);
    }
}

