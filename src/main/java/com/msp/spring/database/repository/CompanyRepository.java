package com.msp.spring.database.repository;

import com.msp.spring.bpp.Auditing;
import com.msp.spring.bpp.InjectBean;
import com.msp.spring.bpp.Transaction;
import com.msp.spring.database.entity.Company;
import com.msp.spring.database.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

   // @Resource(name = "pool2")
    private ConnectionPool connectionPool;

    //@Autowired
    //@Qualifier("pool2")
    //private ConnectionPool pool2;

    @Autowired
    private List<ConnectionPool> pools;

    @Value("${db.pool.size}")
    private int poolSize;

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

    @Autowired
    public void setConnectionPool(ConnectionPool pool2) {
        this.connectionPool = pool2;
    }
}