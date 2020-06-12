package com.gfashion.api.log;

import com.gfashion.api.log.annotation.DDBLog;
import com.gfashion.data.LogEntity;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class DDBLogAspect {

    @Pointcut("within(com.gfashion.api.dynamodb..*)"
            + "@annotation(com.gfashion.api.log.annotation.DDBLog)")
    public void addLog() {
    }

    @Autowired
    GfashionDDBLogResource logResource;

    LogEntity logEntity = new LogEntity();

    @Before("addLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("DATE : " + getDateTime());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteHost());
        log.info("URL : " + request.getRequestURL().toString());
        log.info("CLASS " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("RAW DATA : " + getRaw(request));
        log.info("OBJECT ARGS : " + new Gson().toJson(joinPoint.getArgs()));

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DDBLog ddbLog = method.getAnnotation(DDBLog.class);
        if (ddbLog != null) {
            ddbLog.operationType();
            ddbLog.operationEvent();
            log.info("action type=" + ddbLog.operationType() + ", value=" + ddbLog.operationEvent());
        }

        logEntity.setOperationDate(getDateTime());
        logEntity.setRequestType(request.getMethod());
        logEntity.setOperationUrl(request.getRequestURL().toString());
        logEntity.setOperationClass(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logEntity.setRequestRawInfo(getRaw(request));
        logEntity.setRequestObjectInfo(new Gson().toJson(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "result", pointcut = "addLog()")
    public void doAfterReturning(Object result) throws Throwable {
        String json = new Gson().toJson(((ResponseEntity) result).getBody());
        log.info("RESPONSE : " + json);
        logEntity.setResponseInfo(json);
        saveEntity(logEntity);
    }

    @AfterThrowing(pointcut = "addLog()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        String excption = ExceptionUtils.getStackTrace(throwable);
        log.info("EXCEPTION : " + excption);
        logEntity.setExceptionInfo(excption);
        saveEntity(logEntity);
    }

    private void saveEntity(LogEntity logEntity) {
        logEntity.setId(UUID.randomUUID().toString());
        logResource.createLog(logEntity);
    }

    public static String getDateTime() {
        return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private String getRaw(HttpServletRequest request) {
        try {
            return new String(StreamUtils.copyToByteArray(request.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
