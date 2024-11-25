package com.msp.spring.bpp.example2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DelonghiIron implements Iron {
    @Override
    public void init() {
        System.out.println("DelonghiIron init");
    }

    /*
    IllegalStateException
    методы аннотированные PostConstruct не должны иметь аргументов
     */
    //@PostConstruct
    @Autowired
    /*
    метод сработывает всегда, т.к. Autowired при внедрении аргумента делает метод похожим на сеттер
     */
    private void heat(Water water) {
        System.out.println("heat " + water);
    }
}
