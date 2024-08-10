package com.msp.spring.database.pool;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

public class ConnectionPool implements InitializingBean, DisposableBean {

    private final String userName;
    private final int poolSize;
    private final List<Object> args;
    private /*final*/ Map<String, Object> properties;

    public ConnectionPool() {
        this.userName = "";
        this.poolSize = -1;
        this.args = null;
        this.properties = null;
    }

    public ConnectionPool(String userName,
                          int poolSize,
                          List<Object> args,
                          Map<String, Object> properties) {
        this.userName = userName;
        this.poolSize = poolSize;
        this.args = args;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "ConnectionPool class initialized.";
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * Initialization callback
     * Первый способ через метод - вызывается при инициализации бина.
     * Может быть приватным, т.е. вызывается через рефлекшен
     */
    private void init() {
        System.out.println("ConnectionPool init() method called.");
    }

    /**
     * Initialization callback
     * Второй  способ  - интерфейс InitializingBean.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("ConnectionPool afterPropertiesSet() method called.");
    }

    /**
     * Distraction callback
     * вызывается только при явном закрытии контекста и только у singleton-бинов(тк хранятся в контексте).
     * Первый способ через метод.
     * Может быть приватным, т.е. вызывается через рефлекшен
     */
    private void destroyMethod() {
        System.out.println("ConnectionPool destroyMethod() method called.");
    }

    /**
     * Distraction callback
     * вызывается только при явном закрытии контекста и только у singleton-бинов(тк хранятся в контексте).
     * Второй  способ  - интерфейс DisposableBean.
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("ConnectionPool destroy() method called.");
    }
}
