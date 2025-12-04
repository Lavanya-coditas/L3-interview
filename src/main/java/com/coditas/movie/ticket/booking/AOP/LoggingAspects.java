package com.coditas.movie.ticket.booking.AOP;

import jakarta.persistence.Column;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Slf4j
@Component
public class LoggingAspects
{
    @Pointcut("execution(* com.coditas.movie.ticket.booking.services.*.*(..))")
    public  void ServiceMethod(){};

    @Around("ServiceMethod()")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();
        long executionTime = stopWatch.getTotalTimeMillis();
        if (executionTime > 300) {
            log.warn("Performance : {}.{}() took {} milli seconds",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    joinPoint.getSignature().getName(),
                    executionTime);
        }

        return result;
    }

    @Before("ServiceMethod()")
    public void logMethodArgs(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("Method {}() called with aruments: {}", methodName, args);
    }


    @AfterReturning(pointcut = "ServiceMethod()", returning = "result")
    public void logMethdResponse(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method {} returned : {}", methodName, result);
    }

    @After("ServiceMethod()")
    public void logMethodCompletion(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("Execution of {} .{} complted", className, methodName);
    }

    @Before("ServiceMethod()")
    public void logMethodStart(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("Execution of {}.{} started", className, methodName);
    }
}
