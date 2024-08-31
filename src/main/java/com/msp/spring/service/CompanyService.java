package com.msp.spring.service;

import com.msp.spring.database.dto.CompanyReadDto;
import com.msp.spring.database.repository.CompanyRepository;
import com.msp.spring.listener.entity.AccessType;
import com.msp.spring.listener.entity.EntityEvent;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@ToString
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher publisher;

    @Setter
    private UserService userService;

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
            .map(company -> {
                publisher.publishEvent(new EntityEvent(company, AccessType.CREATE));
                return new CompanyReadDto(company.getId(), company.getName());
            });
    }
}
