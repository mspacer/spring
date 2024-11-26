package com.msp.spring.service.exmpl2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
public class SomeService {

    @Value("${JAVA_HOME}")
    private String javaHome;

    @PostConstruct
    private void init() {
        System.out.println("SomeService init javaHome: " + javaHome);
    }

    @Override
    public String toString() {
        return "SomeService{" +
                "javaHome='" + javaHome + '\'' +
                '}';
    }
}
