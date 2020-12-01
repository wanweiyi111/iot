package com.hzyw.basic.common.aspect;

import com.hzyw.basic.common.properties.AuProperties;
import com.hzyw.basic.service.LogService;
import com.hzyw.basic.util.HttpContextUtil;
import com.hzyw.basic.util.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 *
 */
@Slf4j
@Aspect
@Component
public class LogTokenAspect {

    @Resource
    private AuProperties auProperties;

    @Resource
    private LogService logService;

    @Pointcut("@annotation(com.hzyw.basic.common.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
        // 获取 request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        // 设置 IP 地址
        String ip = IPUtil.getIpAddr(request);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //TODO 获取token方法待完善
       /* if (auProperties.isOpenAopLog()) {
            // 保存日志
            String token = (String) SecurityUtils.getSubject().getPrincipal();
            String username = "";
            if (StringUtils.isNotBlank(token)) {
                username = JWTUtil.getUsername(token);
            }

            SysLogDO log = new SysLogDO();
            log.setUsername(username);
            log.setIp(ip);
            log.setTime(time);
            logService.saveLog(point, log);
        }*/
        return result;
    }
}
