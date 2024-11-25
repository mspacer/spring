package com.msp.spring.service.tmp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IHateInterfacesConfiguration {

    @Bean
    public MissionCriticalService missionCriticalService() {
        return new MissionCriticalService();
    }
}
