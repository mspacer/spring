package com.msp.spring.database.repository;

import com.msp.spring.bpp.Auditing;
import com.msp.spring.bpp.Transaction;
import com.msp.spring.database.entity.Company;
import com.msp.spring.database.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    private final ConnectionPool connectionPool;
    private final List<ConnectionPool> pools;
    private final int poolSize;

    public CompanyRepository(ConnectionPool connectionPool,
                             List<ConnectionPool> pools,
                             @Value("${db.pool.size}") int poolSize) {
        this.connectionPool = connectionPool;
        this.pools = pools;
        this.poolSize = poolSize;
    }

    @PostConstruct
    private void init() {
        System.out.println("CompanyRepository init");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("Company findById " + id);
        return Optional.of(new Company(10));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete company " + entity);
    }
}