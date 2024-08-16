package com.msp.spring.service;

import com.msp.spring.database.entity.Company;
import com.msp.spring.database.repository.CrudRepository;
import com.msp.spring.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;
    private final CompanyService companyService;

    @PostConstruct
    private void init() {
        //решение проблемы циклической зависимости
        companyService.setUserService(this);
    }
}
