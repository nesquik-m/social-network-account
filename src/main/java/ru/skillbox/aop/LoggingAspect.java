package ru.skillbox.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("@annotation(logAspect)")
    public void logMethod(JoinPoint joinPoint, LogAspect logAspect) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        switch (logAspect.type()) {
            case CONTROLLER -> {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                String httpMethod = request.getMethod();
                String requestURL = request.getRequestURI();
                log.info("CONTROLLER {} | Method: {} | URL: {} | HTTP Method: {} | Args: {}",
                        joinPoint.getTarget().getClass().getSimpleName(),
                        methodName,
                        requestURL,
                        httpMethod,
                        args);
            }
            case SERVICE -> {
                log.info("SERVICE {} | Method: {} | Args: {}",
                        joinPoint.getTarget().getClass().getSimpleName(),
                        methodName,
                        args);
            }
        }
    }
}
