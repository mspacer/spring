package com.msp.spring.service.tmp;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MissionCriticalService {

    @PostConstruct
    private void init() {
        System.out.println("Don't forget turn off the iron");
    }
}
