package com.msp.spring.service.exmpl4;

import org.springframework.stereotype.Component;

@Component
//@DeprecatedClass(newImpl = T1000.class)
@BeanClass(T1000.class)
public class T800 implements Terminator {

    @Override
    public String name() {
        return "Я робот";
    }
}
