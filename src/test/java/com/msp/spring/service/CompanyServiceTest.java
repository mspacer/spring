package com.msp.spring.service;

import com.msp.spring.database.dto.CompanyReadDto;
import com.msp.spring.database.entity.Company;
import com.msp.spring.database.repository.CompanyRepository;
import com.msp.spring.listener.entity.EntityEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({
        MockitoExtension.class
})
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private UserService userService;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void findById() {
        Optional<Company> company = Optional.of(new Company(1, "", null));
        CompanyReadDto expectedDto = new CompanyReadDto(1);
        Mockito.doReturn(company).when(companyRepository).findById(1);

        Optional<CompanyReadDto> actualResult = companyService.findById(1);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(dto -> assertEquals(expectedDto, dto));
        Mockito.verify(publisher).publishEvent(Mockito.any(EntityEvent.class));
        Mockito.verifyNoInteractions(userService);
    }
}