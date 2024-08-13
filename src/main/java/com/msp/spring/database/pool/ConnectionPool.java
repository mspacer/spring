package com.msp.spring.database.pool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ConnectionPool {

    private final String userName;
    private final int poolSize;

    public ConnectionPool() {
        this.userName = "";
        this.poolSize = -1;
    }

    @Autowired
    public ConnectionPool(@Value("${db.username}") String userName,
                          @Value("${db.pool.size}") int poolSize) {
        this.userName = userName;
        this.poolSize = poolSize;
    }


    /**
     * Initialization callback
     * Первый способ через метод - вызывается при инициализации бина.
     * Может быть приватным, т.е. вызывается через рефлекшен
     *
     * Предпочтительный способ использование аннотации, обрабатывается первым
     */
    @PostConstruct
    private void init() {
        System.out.println("ConnectionPool init() method called.");
    }

    /**
     * Distraction callback
     * вызывается только при явном закрытии контекста и только у singleton-бинов(тк хранятся в контексте).
     * Первый способ через метод.
     * Может быть приватным, т.е. вызывается через рефлекшен
     *
     * Предпочтительный способ использование аннотации, обрабатывается первым
     */
    @PreDestroy
    private void destroyMethod() {
        System.out.println("ConnectionPool destroyMethod() method called.");
    }

    @Override
    public String toString() {
        return "ConnectionPool class initialized.";
    }

}
