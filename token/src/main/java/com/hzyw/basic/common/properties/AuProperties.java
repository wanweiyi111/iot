package com.hzyw.basic.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuProperties {

    private ShiroProperties shiro = new ShiroProperties();

    private boolean openAopLog = true;

}
