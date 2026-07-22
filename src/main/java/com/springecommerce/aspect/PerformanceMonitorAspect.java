package com.springecommerce.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitorAspect {

    private static final Logger log = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

    @Around("execution(* com.springecommerce.service.AuthenticationService.register(..))")
    public Object monitorTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

       Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        long totalTime = endTime - startTime;

        log.info("Time taken by: " + joinPoint.getSignature().getName() + " is: " + totalTime);

        return result;

        

    }
}
