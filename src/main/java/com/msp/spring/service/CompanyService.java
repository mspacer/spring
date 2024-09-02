package com.msp.spring.service;

import com.msp.spring.database.dto.CompanyReadDto;
import com.msp.spring.database.dto.UserFilter;
import com.msp.spring.database.repository.CompanyRepository;
import com.msp.spring.listener.entity.AccessType;
import com.msp.spring.listener.entity.EntityEvent;
import com.msp.spring.mapper.CompanyReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ToString
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher publisher;
    private final CompanyReadMapper mapper;

    @Setter
    private UserService userService;

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
            .map(company -> {
                publisher.publishEvent(new EntityEvent(company, AccessType.CREATE));
                return new CompanyReadDto(company.getId(), company.getName());
            });
    }

    public List<CompanyReadDto> findAll() {
        return companyRepository.findAll().stream()
                                .map(mapper::map)
                                .toList();
    }
}
