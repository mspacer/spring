package com.msp.spring.bpp.example1;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class Son extends Parent {

    @PostConstruct
    public void init() {
        System.out.println("Son init");
    }
}
