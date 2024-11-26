package com.msp.spring.service.exmpl1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaruhConfig {

    @Bean
    @BaruhQualifier
    public String str1() {
        return "Google";
    }

    @Bean
    @BaruhQualifier
    public String str2() {
        return "Amazon";
    }

    @Bean
    @BaruhQualifier
    public String str3() {
        return "Meta";
    }
}
