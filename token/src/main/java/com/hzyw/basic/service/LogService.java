package com.hzyw.basic.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hzyw.basic.dos.LogDO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public interface LogService {


    void deleteLogs(String[] logIds);

    @Async
    void saveLog(ProceedingJoinPoint point, LogDO log) throws JsonProcessingException;
}
