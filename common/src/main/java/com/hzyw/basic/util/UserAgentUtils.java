package com.hzyw.basic.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 终端设备，用户代理（User-Agent）工具类
 * @author male
 */
public class UserAgentUtils {

    /**
     * \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
     * 字符串在编译时会被转码一次,所以是 "\\b"
     * \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
     */
    private static final String PHONE_REG = "\\b(ip(hone|od)|android|opera m(ob|in)i"
            + "|windows (phone|ce)|blackberry"
            + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            + "|laystation portable)|nokia|fennec|htc[-_]"
            + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    private static final String TABLE_REG = "\\b(ipad|tablet|(Nexus 7)|up.browser"
            + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    /**
     * 移动设备正则匹配
     */
    private static final Pattern PHONE_PAT = Pattern.compile(PHONE_REG, Pattern.CASE_INSENSITIVE);
    private static final Pattern TABLE_PAT = Pattern.compile(TABLE_REG, Pattern.CASE_INSENSITIVE);

    private static final String USER_AGENT = "ua";
    private static final String PC = "pc";
    private static final String MOBILE = "mobile";

    /**
     * 检测是否是移动设备访问
     *
     * @param userAgent 浏览器标识
     * @return true:移动设备接入，false:PC端接入
     */
    private static boolean checked(String userAgent) {
        if (null == userAgent) {
            userAgent = "";
        }

        Matcher matcherPhone = PHONE_PAT.matcher(userAgent);
        Matcher matcherTable = TABLE_PAT.matcher(userAgent);
        return matcherPhone.find() || matcherTable.find();
    }

    /**
     * 检查访问方式是否为移动端
     *
     * @param request http请求对象
     * @return true:移动设备接入，false:PC端接入
     */
    public static boolean isMobile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userAgentFromSession = session.getAttribute(USER_AGENT);
        String userAgentFromHeader = request.getHeader("USER-AGENT");

        String userAgent;
        if (userAgentFromSession != null) {
            userAgent = userAgentFromSession.toString();
        } else {
            userAgent = userAgentFromHeader.toLowerCase();
        }

        boolean isFromMobile = checked(userAgent);

        if (userAgentFromSession == null) {
            if (isFromMobile) {
                session.setAttribute(USER_AGENT, MOBILE);
            } else {
                session.setAttribute(USER_AGENT, PC);
            }
        }

        return isFromMobile;
    }
}