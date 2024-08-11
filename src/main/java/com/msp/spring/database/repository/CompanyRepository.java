package com.msp.spring.database.repository;

import com.msp.spring.bpp.Auditing;
import com.msp.spring.bpp.InjectBean;
import com.msp.spring.bpp.Transaction;
import com.msp.spring.database.entity.Company;
import com.msp.spring.database.pool.ConnectionPool;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    // инициализация параметра через обработку аннотации посредством bean postprocessor (InjectBeanPostProcessor)
    @InjectBean
    private ConnectionPool connectionPool;

    @PostConstruct
    private void init() {
        System.out.println("CompanyRepository init");
    }

/*
    @Override
    public String toString() {
        return "CompanyRepository class initialized.";
    }
*/

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