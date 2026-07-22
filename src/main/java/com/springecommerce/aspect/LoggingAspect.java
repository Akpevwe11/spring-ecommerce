package com.springecommerce.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.springecommerce.service.AuthenticationService.register(..))")
    public void logRegister(JoinPoint jp) {
        log.info("Method callled" + jp.getSignature().getName());
    }

    @After("execution(* com.springecommerce.service.AuthenticationService.register(..))")
    public void logRegisterAfter(JoinPoint jp) {
        log.info("Method executed" + jp.getSignature().getName());
    }

    @AfterThrowing("execution(* com.springecommerce.service.AuthenticationService.register(..))")
    public void logRegisterException(JoinPoint jp) {
        log.error("Method has some issues" + jp.getSignature().getName() + " with exception: ");
    }

    @AfterReturning(pointcut = "execution(* com.springecommerce.service.AuthenticationService.register(..))", returning = "result")
    public void logRegisterResult(JoinPoint jp, Object result) {
        log.info("Method executed successfully" + jp.getSignature().getName() + " with result: " + result);
    }

    @Around("execution(* com.springecommerce.controller..*(..))")
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
