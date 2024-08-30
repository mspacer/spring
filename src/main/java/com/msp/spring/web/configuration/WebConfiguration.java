package com.msp.spring.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("web")
public class WebConfiguration {

    @Bean
    public String webConfiguration() {
        return "It`s import from webConfiguration";
    }
}
