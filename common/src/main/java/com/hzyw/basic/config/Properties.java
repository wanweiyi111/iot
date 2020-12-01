package com.hzyw.basic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author male
 */
@Configuration
public class Properties {

    @Value("${spring.jackson.date-format}")
    private String dateFormat;
    @Value("${app.name}")
    private String appName;
    @Value("${app.description}")
    private String appDescription;
    @Value("${config.url.token}")
    private String tokenUrl;
    @Value("${config.url.log}")
    private String logUrl;
    @Value("${aspect.authority.not-app}")
    private String[] noAuthorityApp;
    @Value("${aspect.authority.project-is-aspect}")
    private String isAspectAuthority;
    @Value("${aspect.log-record.not-app}")
    private String[] noRecordApp;
    @Value("${aspect.log-record.project-is-aspect}")
    private String isAspectRecord;
    @Value("${config.principal-name}")
    private String principalName;
    public static final String SKIP_AUTH = "SKIPAUTH";
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String APP_NAME;
    public static String APP_DESCRIPTION;
    public static String TOKEN_URL;
    public static String LOG_URL;
    public static String[] NO_AUTHORITY_APP;
    public static String[] NO_RECORD_APP;
    public static String IS_ASPECT_AUTHORITY;
    public static String IS_ASPECT_RECORD;
    public static String PRINCIPAL_NAME;

    @PostConstruct
    public void init() {
        DATE_FORMAT = dateFormat;
        APP_NAME = appName;
        APP_DESCRIPTION = appDescription;
        TOKEN_URL = tokenUrl;
        LOG_URL = logUrl;
        NO_AUTHORITY_APP = noAuthorityApp;
        NO_RECORD_APP = noRecordApp;
        IS_ASPECT_AUTHORITY = isAspectAuthority;
        IS_ASPECT_RECORD = isAspectRecord;
        PRINCIPAL_NAME = principalName;
    }
}