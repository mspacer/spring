package com.msp.spring.service;

import com.msp.spring.database.entity.Company;
import com.msp.spring.database.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CrudRepository<Integer, Company> companyRepository;

    //Циклическая зависимость. Через конструктор нельзя
    //@Autowired - первый вариант, либо чере set
    private /*final*/ UserService userService;

    public CompanyService(CrudRepository<Integer, Company> companyRepository
                          /*UserService userService*/) {
        this.companyRepository = companyRepository;
        /*this.userService = userService;*/
    }

    @Override
    public String toString() {
        return "CompanyService class initialized.\n" +
                (companyRepository != null ? companyRepository.toString() : null) + "\n" +
                (userService != null ? userService.toString() : null);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
