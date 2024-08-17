package com.msp.spring.database.pool;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@RequiredArgsConstructor
@ToString
@Slf4j
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
        log.info("ConnectionPool init() method called.");
    }

    @PreDestroy
    private void destroyMethod() {
        log.info("ConnectionPool destroyMethod() method called.");
    }

}
