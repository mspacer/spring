package com.msp.spring.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
//@Profile("web")
public class WebConfiguration implements WebMvcConfigurer {

/*
    @Bean
    public String webConfiguration() {
        return "It`s import from webConfiguration";
    }
*/

    @Override
    public void addFormatters(FormatterRegistry registry) {
      //  registry.addConverter(Jsr310Converters.StringToLocalDateConverter.INSTANCE);
       // registry.addConverter(String.class, LocalDate.class,
       //         source -> LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
