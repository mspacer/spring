package com.msp.spring.integration.database.repository;

import com.msp.spring.IT;
import com.msp.spring.database.entity.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@Slf4j
class CompanyRepositoryTest {

    private final EntityManager entityManager;

    @Test
    //@Transactional
    void findById() {
        //spring не позволяет ручное открытие трансакции, но без нее будет ошиба LazyInitializationException на
        // locales (ElementCollection fetch=Lazy по умолчанию)
        //entityManager.getTransaction().begin();
        Company company = entityManager.find(Company.class, 1);
        log.debug("test company: {}", company);
    }

    @Test
    //@Commit
    void save() {
        var company = Company.builder()
                .name("Apple_test")
                .locales(Map.of(
                        "ru", "Apple описание",
                        "en", "Apple description"
                ))
                .build();
        entityManager.persist(company);
        assertNotNull(company.getId());
    }

    @BeforeTransaction
    private void beforeTransaction() {
        log.debug("beforeTransaction");
    }

    @AfterTransaction
    private void afterTransaction() {
        log.debug("afterTransaction");
    }

}