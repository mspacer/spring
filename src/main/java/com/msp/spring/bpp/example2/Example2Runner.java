package com.msp.spring.bpp.example2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example2Runner {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.msp.spring.bpp.example2");
        System.out.println("Ok");
    }
}
