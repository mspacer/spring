package com.msp.spring.service;

import com.msp.spring.database.dto.CompanyReadDto;
import com.msp.spring.database.entity.Company;
import com.msp.spring.database.repository.CrudRepository;
import com.msp.spring.listener.entity.AccessType;
import com.msp.spring.listener.entity.EntityEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private final CrudRepository<Integer, Company> companyRepository;
    private final ApplicationEventPublisher publisher;
    private UserService userService;

    public CompanyService(CrudRepository<Integer, Company> companyRepository,
                            ApplicationEventPublisher publisher) {
        this.companyRepository = companyRepository;
        this.publisher = publisher;
    }

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
            .map(company -> {
                publisher.publishEvent(new EntityEvent(company, AccessType.CREATE));
                return new CompanyReadDto(company.getId());
            });
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
