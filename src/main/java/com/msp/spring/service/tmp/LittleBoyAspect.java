package com.msp.spring.service.tmp;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LittleBoyAspect {

    @After("execution(* com.msp.spring.service.tmp.*.*(..))")
    public void whyInterfaces() {
        System.out.println("proxy works without interface");
    }
}
