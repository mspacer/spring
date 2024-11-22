package com.msp.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class CompanyAspect {

    @Pointcut("execution(public * com.msp.spring.database.repository.*Repository.findById(*))")
    public void anyFindByIdServiceMethod() {
    }

    @Before(value = "anyFindByIdServiceMethod()")
    public void addLogging(JoinPoint joinPoint) {
        System.out.println("Aspect before findById method args: " + argsToString(joinPoint));
    }

    public static String argsToString(JoinPoint joinPoint) {
        String args = Arrays.stream(joinPoint.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        return "joinPoint " + joinPoint.toString() + ", args=[" + args + "]";
    }
}
