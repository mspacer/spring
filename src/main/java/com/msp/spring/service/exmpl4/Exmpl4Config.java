package com.msp.spring.service.exmpl4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Exmpl4Config {

    @Bean
    //@BeanClass(T1000.class) // можно получить в BFPP
    public Terminator terminator() {
        return new T1000();
    }

}
