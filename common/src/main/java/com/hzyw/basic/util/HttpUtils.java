package com.hzyw.basic.util;

import com.google.common.net.HttpHeaders;
import com.hzyw.basic.config.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Http工具类
 * @author haoyuan, weianqi, male
 */
@Slf4j
public class HttpUtils {

    private static final String TOKEN_HEADER_NAME = "Authentication";
    private static final int ONE_SECOND = 1000;
    private static final int DEFAULT_TIMEOUT_SECOND = 5;
    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
    public static final String HTTP_OK = String.valueOf(HttpStatus.SC_OK);

    /**
     * 发送Get请求
     *
     * @param url  请求url
     * @return 结果
     */
    public static String doGet(String url) {
        return doHttp(new HttpGet(url));
    }

    /**
     * 发送Get请求
     *
     * @param url  请求url
     * @param isAuthorize 是否鉴权
     * @return 结果
     */
    public static String doGet(String url, boolean isAuthorize) {
        return doHttp(new HttpGet(url), isAuthorize);
    }

    /**
     * 发送Get请求
     *
     * @param url  请求url
     * @param timeout 超时时间（秒）
     * @return 结果
     */
    public static String doGet(String url, Integer timeout) {
        return doHttp(new HttpGet(url), timeout);
    }

    /**
     * 发送Get请求
     *
     * @param url  请求url
     * @param isAuthorize 是否鉴权
     * @param timeout 超时时间（秒）
     * @return 结果
     */
    public static String doGet(String url, boolean isAuthorize, Integer timeout) {
        return doHttp(new HttpGet(url), isAuthorize, timeout);
    }

    /**
     * 发送POST请求
     *
     * @param url  请求url
     * @param data 请求数据
     * @return 结果
     */
    @SuppressWarnings("unused")
    public static String doPost(String url, String data) {
        HttpPost httpPost = new HttpPost(url);
        if (!StringUtils.isEmpty(data)) {
            httpPost.setEntity(new StringEntity(data, StandardCharsets.UTF_8));
        }

        return doHttp(httpPost);
    }

    /**
     * 发送POST请求
     *
     * @param url  请求url
     * @param isAuthorize 是否鉴权
     * @return 结果
     */
    public static String doPost(String url, String data, boolean isAuthorize) {
        HttpPost httpPost = new HttpPost(url);
        if (!StringUtils.isEmpty(data)) {
            httpPost.setEntity(new StringEntity(data, StandardCharsets.UTF_8));
        }

        return doHttp(httpPost, isAuthorize);
    }
    public static String doPost(String url, String data, boolean isAuthorize,int timeout) {
        HttpPost httpPost = new HttpPost(url);
        if (!StringUtils.isEmpty(data)) {
            httpPost.setEntity(new StringEntity(data, StandardCharsets.UTF_8));
        }

        return doHttp(httpPost, isAuthorize,timeout);
    }

    /**
     * 发送Delete请求
     *
     * @param url  请求url
     * @return 结果
     */
    @SuppressWarnings("unused")
    public static String doDelete(String url) {
        return doHttp(new HttpDelete(url));
    }

    /**
     * 发送Delete请求
     *
     * @param url  请求url
    * @param isAuthorize 是否鉴权
     * @return 结果
     */
    public static String doDelete(String url, boolean isAuthorize) {
        return doHttp(new HttpDelete(url), isAuthorize);
    }

    /**
     * 解析出url参数中的键值对
     *
     * @param url url参数
     * @return 键值对
     */
    @SuppressWarnings("unused")
    public static Map<String, String> getRequestParam(String url) {

        Map<String, String> map = new HashMap<>(16);
        String[] arrSplit;

        // 每个键值为一组
        arrSplit = url.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual;
            arrSplitEqual = strSplit.split("[=]");

            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                map.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (StringUtils.isNotBlank(arrSplitEqual[0])) {
                    map.put(arrSplitEqual[0], "");
                }
            }
        }
        return map;
    }

    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        RequestContextHolder.setRequestAttributes(requestAttributes, true);
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static String getRealClientIp() {
        HttpServletRequest request = getCurrentRequest();

        String ip = Optional.ofNullable(request.getHeader(HttpHeaders.X_FORWARDED_FOR)).orElse(request.getRemoteAddr());
        return ip.split(",")[0];
    }

    public static String getCurrentToken() {
        HttpServletRequest request = getCurrentRequest();
        String token = request.getHeader(TOKEN_HEADER_NAME);

        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }

        return token;
    }

    private static String doHttp(HttpRequestBase httpRequestBase) {
        return doHttp(httpRequestBase, true, DEFAULT_TIMEOUT_SECOND);
    }

    private static String doHttp(HttpRequestBase httpRequestBase, boolean isAuthorize) {
        return doHttp(httpRequestBase, isAuthorize, DEFAULT_TIMEOUT_SECOND);
    }

    private static String doHttp(HttpRequestBase httpRequestBase, Integer timeout) {
        return doHttp(httpRequestBase, true, timeout);
    }

    private static String doHttp(HttpRequestBase httpRequestBase, boolean isAuthorize, Integer timeout) {
        String responseText = StringUtils.EMPTY;

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(timeout * ONE_SECOND).setSocketTimeout(timeout * ONE_SECOND)
                .setConnectionRequestTimeout(timeout * ONE_SECOND).build();
        httpRequestBase.setConfig(requestConfig);

        httpRequestBase.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

        if (isAuthorize) {
            httpRequestBase.addHeader(HttpHeaders.X_FORWARDED_FOR, getRealClientIp());
            httpRequestBase.addHeader(TOKEN_HEADER_NAME, getCurrentToken());
        } else {
            httpRequestBase.addHeader(TOKEN_HEADER_NAME, Properties.SKIP_AUTH);
        }

        try (CloseableHttpResponse response = HTTP_CLIENT.execute(httpRequestBase)) {
            responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(httpRequestBase.toString(), e);
        }

        return responseText;
    }
}