package com.msp.spring.bpp.example1;

import com.msp.spring.listener.PostInitialisation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

public class Son extends Parent {

    @Transactional
    /*
    метод не будет вызван как транзакционный, поскольку инит фаза @PostConstruct вызывается
    ДО postProcessAfterInitialization BPP, в котором по соглашению класс становиться прокси,
    а метод - трансакционным
     */
    @PostConstruct
    public void init() {
        System.out.println("Son init");
    }
    /*
    Вариант решения - использование листенера, который вызывается ПОСЛЕ конфигуриврования бина через BPP
    ApplicationListener<ContextRefreshedEvent>
     */
    @Transactional
    @PostInitialisation
    public void postInit() {
        System.out.println("Son post init");
    }

    @Transactional
    public void doRequest() {
        System.out.println("transaction doRequest");
    }
}
