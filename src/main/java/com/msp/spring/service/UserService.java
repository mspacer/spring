package com.msp.spring.service;

import com.msp.spring.database.pool.ConnectionPool;
import com.msp.spring.database.repository.CompanyRepository;
import com.msp.spring.database.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public UserService() {
        // UserService управляет созданием XXXRepository и ConnectionPool, что плохо
        // Также могут быть другие классы, требующие эти объекты
        userRepository = new UserRepository(new ConnectionPool());
        companyRepository = null; //new CompanyRepository(new ConnectionPool());
    }

    public UserService(UserRepository userRepository, CompanyRepository companyRepository) {
        // зависимоти должны передаваться в конструктор
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public String toString() {
        return "UserService class initialized.\n" +
                userRepository.toString() + "\n" +
                companyRepository.toString();
    }
}
