package com.msp.spring.integration.database.repository;

import com.msp.spring.IT;
import com.msp.spring.database.entity.Company;
import com.msp.spring.database.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
@Slf4j
class CompanyRepositoryTest {

    private final EntityManager entityManager;
    private final CompanyRepository companyRepository;

    @Test
    void checkFindByQueries() {
        Optional<Company> google = companyRepository.findByName("Google");
        List<Company> companies = companyRepository.findAllByNameIsContainingIgnoringCase("a");
        assertTrue(google.isPresent() && !companies.isEmpty());
    }

    @Test
    void delete() {
        var company = Company.builder()
                .name("Apple_test")
                .locales(Map.of(
                        "ru", "Apple описание",
                        "en", "Apple description"
                ))
                .build();
        entityManager.persist(company);
        Integer appleId = company.getId();
        entityManager.flush();

        final org.hibernate.engine.spi.SessionImplementor session = entityManager.unwrap( org.hibernate.engine.spi.SessionImplementor.class );
        final org.hibernate.engine.spi.PersistenceContext pc = session.getPersistenceContext();

/*
        Optional<Company> optionalExpectedCompany = companyRepository.findById(appleId);
        assertTrue(optionalExpectedCompany.isPresent());


        optionalExpectedCompany.ifPresent(companyRepository::delete);
        entityManager.flush();

        optionalExpectedCompany = companyRepository.findById(appleId);
        assertFalse(optionalExpectedCompany.isPresent());
*/

        assertTrue(companyRepository.existsById(appleId));

        companyRepository.deleteById(appleId);
        entityManager.flush();

        assertFalse(companyRepository.existsById(appleId));

    }

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