package com.msp.spring.service.exmpl4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Exampl4Runner {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.msp.spring.service.exmpl4");

        Terminator terminator = context.getBean("t800", Terminator.class);
        System.out.println(terminator.name());

        Terminator terminator2 = context.getBean("terminator", Terminator.class);
        System.out.println(terminator2.name());

    }
}
