package com.msp.spring.integration.service;

import com.msp.spring.IT;
import com.msp.spring.config.DatabaseProperties;
import com.msp.spring.database.dto.CompanyReadDto;
import com.msp.spring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

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