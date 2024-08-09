package com.msp.spring.database.repository;

import com.msp.spring.database.pool.ConnectionPool;

public class CompanyRepository {

    private final ConnectionPool connectionPool;

    public CompanyRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public String toString() {
        return "CompanyRepository class initialized.";
    }

}
