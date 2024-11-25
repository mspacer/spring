package com.msp.spring.bpp.example2;

import org.springframework.stereotype.Component;

//@Component
public class LgIron implements Iron {
    @Override
    public void init() {
        System.out.println("heat slowly");
    }
}
