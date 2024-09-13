package com.msp.spring.config;

import com.msp.spring.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Configuration
@EnableJpaAuditing/*(auditorAwareRef = "auditorAware")*/ // если он один - spring сам его найдет
@EnableEnversRepositories(basePackageClasses = ApplicationContextRunner.class)
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> (UserDetails)authentication.getPrincipal())
                .map(UserDetails::getUsername);
                //.map(authentication -> authentication.getName());
    }

/*
    @Bean
    public AuditorAware<String> auditorAware2() {
        return () -> Optional.of("Петров Петр Петравич");
    }
*/

}
