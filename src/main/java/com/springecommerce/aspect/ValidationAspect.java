package com.springecommerce.aspect;

import com.springecommerce.dto.RegisterRequest;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;

@Component
@Aspect
public class ValidationAspect {

    private static final Logger log = LoggerFactory.getLogger(ValidationAspect.class);

    @Around("execution(* com.springecommerce.service.AuthenticationService.register(..))")
    public Object validateAndUpdate(ProceedingJoinPoint jp) throws Throwable {

        Object[] args = jp.getArgs();

        // If register() takes RegisterRequest as argument:
        RegisterRequest request = (RegisterRequest) args[0];

        // Access the fields:
        String firstName = request.getFirstName();
        String email = request.getEmail();
        String password = request.getPassword();

        // Perform validation
        if (firstName == null || firstName.trim().isEmpty()) {
            log.error("Validation failed: First name is required");
            throw new IllegalArgumentException("First name is required");
        }
        if (email == null || email.trim().isEmpty()) {
            log.error("Validation failed: Email is required");
            throw new IllegalArgumentException("Email is required");
        }
        if (password == null || password.trim().isEmpty()) {
            log.error("Validation failed: Password is required");
            throw new IllegalArgumentException("Password is required");
        }

        // Proceed with the actual method
        return jp.proceed();
    }


}
