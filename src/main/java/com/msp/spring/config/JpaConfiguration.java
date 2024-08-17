package com.msp.spring.config;

import com.msp.spring.config.condition.JpaCondition;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@Conditional(JpaCondition.class)
public class JpaConfiguration {
    //private static final Logger log = org.slf4j.LoggerFactory.getLogger(JpaConfiguration.class);

    @PostConstruct
    private void init() {
        log.info("JpaConfiguration is enabled.");
    }
}
