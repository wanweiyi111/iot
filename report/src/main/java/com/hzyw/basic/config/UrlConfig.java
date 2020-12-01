package com.hzyw.basic.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;

/**
 * @author hao yuan
 * @date 2019.08.07
 * 定义mqtt连接与定义消息接收通道
 */
@Configuration
@IntegrationComponentScan
@Slf4j
@Data
public class UrlConfig {

    @Value("${config.url.light}")
    private String lightUrl;

    @Value("${config.url.screen}")
    private String screenUrl;

    @Value("${config.url.camera}")
    private String cameraUrl;

//    @Value("${config.url.wifi}")
//    private String wifi_url;

    @Value("${config.url.sensor}")
    private String sensorUrl;

    @Value("${config.url.radar}")
    private String radarUrl;

    @Value("${config.url.lightCount}")
    private String light_count_url;

    @Value("${config.url.etc}")
    private String etcUrl;


}
