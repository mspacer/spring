package com.msp.spring.database.repository;

import com.msp.spring.database.pool.ConnectionPool;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final ConnectionPool connectionPool;

    public UserRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public String toString() {
        return "UserRepository class initialized.";
    }

}
