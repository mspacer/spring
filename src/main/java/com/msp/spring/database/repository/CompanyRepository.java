package com.msp.spring.database.repository;

import com.msp.spring.database.pool.ConnectionPool;

public class CompanyRepository {

    private final ConnectionPool connectionPool;

    public CompanyRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * фабричный метод
     * @param connectionPool
     * @return
     */
    public static CompanyRepository of(ConnectionPool connectionPool) {
        System.out.println("execute CompanyRepository.of(...)");
        return new CompanyRepository(connectionPool);
    }

    @Override
    public String toString() {
        return "CompanyRepository class initialized.";
    }

}
