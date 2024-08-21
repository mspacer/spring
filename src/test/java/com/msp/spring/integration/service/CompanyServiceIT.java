package com.msp.spring.integration.service;

import com.msp.spring.ApplicationContextRunner;
import com.msp.spring.IT;
import com.msp.spring.config.DatabaseProperties;
import com.msp.spring.database.dto.CompanyReadDto;
import com.msp.spring.database.entity.Company;
import com.msp.spring.database.repository.CrudRepository;
import com.msp.spring.listener.entity.EntityEvent;
import com.msp.spring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*@SpringJUnitConfig(
        value = {ApplicationContextRunner.class},
        initializers = {ConfigDataApplicationContextInitializer.class}
)*/
/*
@ExtendWith({
        SpringExtension.class
})
@ContextConfiguration(classes = ApplicationContextRunner.class,
                    initializers = ConfigDataApplicationContextInitializer.class)
*/
/*
@SpringBootTest
@ActiveProfiles("test")
*/
@IT
@RequiredArgsConstructor
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CompanyServiceIT {

    private final CompanyService companyService;
    private final DatabaseProperties databaseProperties;
    private final ApplicationContext applicationContext;

    @Test
    void findById() {
        CompanyReadDto expectedDto = new CompanyReadDto(1);

        Optional<CompanyReadDto> actualResult = companyService.findById(1);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(dto -> assertEquals(expectedDto, dto));
    }
}