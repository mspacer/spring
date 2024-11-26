package com.msp.spring.service.exmpl2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Exampl2Runner {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.msp.spring.service.exmpl2");

        System.out.println(context.getBean(SomeService.class));

        System.out.println("ok ");
    }
}
