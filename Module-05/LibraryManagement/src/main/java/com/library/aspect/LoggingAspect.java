package com.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Exercise 3: LoggingAspect to track method execution times
 * Exercise 8: Advice methods for logging before and after method execution
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Pointcut definition targeting all methods in com.library package
     */
    @Pointcut("execution(* com.library.*.*(..))")
    public void libraryPointcut() {}

    /**
     * Exercise 8: Before advice - logs method name before execution
     */
    @Before("libraryPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[AOP - Before] Entering method: "
                + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());
    }

    /**
     * Exercise 8: After advice - logs method name after execution
     */
    @After("libraryPointcut()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[AOP - After]  Exiting method:  "
                + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());
    }

    /**
     * Exercise 3: Around advice - logs method execution time
     */
    @Around("libraryPointcut()")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("[AOP - Around] Method: "
                + proceedingJoinPoint.getSignature().getDeclaringTypeName()
                + "." + proceedingJoinPoint.getSignature().getName()
                + " executed in " + executionTime + " ms");

        return result;
    }

    /**
     * Exercise 8: AfterReturning advice - logs return value after successful execution
     */
    @AfterReturning(pointcut = "libraryPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("[AOP - AfterReturning] Method: "
                + joinPoint.getSignature().getName()
                + " returned: " + result);
    }
}
