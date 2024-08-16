package com.msp.spring.database.pool;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@RequiredArgsConstructor
@ToString
public class ConnectionPool {

    /*
    чтобы ломбок при генерации конструктора подставил в него аннотацию @Value,
    требует lombok.config
     */
    @Value("${db.username}")
    private final String userName;
    @Value("${db.pool.size}")
    private final Integer poolSize;

    @PostConstruct
    private void init() {
        System.out.println("ConnectionPool init() method called.");
    }

    @PreDestroy
    private void destroyMethod() {
        System.out.println("ConnectionPool destroyMethod() method called.");
    }

}
