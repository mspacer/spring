package com.msp.spring.service.exmpl1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class JekaConfig {

    @Bean
    public String str() {
        return "Jeka";
    }

    @Bean
    @JekaQualifier
    public List<String> list() {
        return Arrays.asList("Groovy", "Spring");
    }

    @Bean
    public ArrayList<String> list1() {
        return new ArrayList(Arrays.asList("Groovy-1", "Spring-1"));
    }
}
