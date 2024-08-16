package com.msp.spring.config;

import com.msp.spring.config.condition.JpaCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Conditional(JpaCondition.class)
public class JpaConfiguration {

    @PostConstruct
    private void init() {
        System.out.println("JpaConfiguration is enabled.");
    }
}
