package com.msp.spring.database.pool;

import java.util.List;
import java.util.Map;

public class ConnectionPool {

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
}
