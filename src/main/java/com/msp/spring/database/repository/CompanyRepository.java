package com.msp.spring.database.repository;

import com.msp.spring.bpp.Auditing;
import com.msp.spring.bpp.Transaction;
import com.msp.spring.database.entity.Company;
import com.msp.spring.database.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    private final ConnectionPool connectionPool;
    private final List<ConnectionPool> pools;
    @Value("17")
    private final Integer poolSize;

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

    @Override
    public String toString() {
        return "CompanyRepository class initialized.";
    }

}