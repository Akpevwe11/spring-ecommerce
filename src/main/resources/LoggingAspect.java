package com.codewithmosh.springecommerce.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.codewithmosh.springecommerce.controller..*(..))")
    public Object logControllers(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();
        log.info("→ {}", method);
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            log.info("← {} ({} ms)", method, System.currentTimeMillis() - start);
            return result;
        } catch (Throwable ex) {
            log.error("✗ {} failed: {}", method, ex.getMessage());
            throw ex;
        }
    }
}
