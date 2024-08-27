package com.msp.spring.config;

import com.msp.spring.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing/*(auditorAwareRef = "auditorAware")*/ // если он один - spring сам его найдет
@EnableEnversRepositories(basePackageClasses = ApplicationContextRunner.class)
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("Иванов Иван Иванович");
    }

/*
    @Bean
    public AuditorAware<String> auditorAware2() {
        return () -> Optional.of("Петров Петр Петравич");
    }
*/

}
