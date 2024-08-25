package com.msp.spring.integration.database.repository;

import com.msp.spring.IT;
import com.msp.spring.database.entity.Role;
import com.msp.spring.database.entity.User;
import com.msp.spring.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.PersistenceContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryTest {

    private final EntityManager entityManager;
    private final UserRepository userRepository;

    private PersistenceContext pc;

    @BeforeEach
    void init() {
        final org.hibernate.engine.spi.SessionImplementor session = entityManager.unwrap( org.hibernate.engine.spi.SessionImplementor.class );
        pc = session.getPersistenceContext();
    }

    @Test
    void findByName() {
        List<User> users = userRepository.findAllBy("a", "ov");
        assertFalse(users.isEmpty());

        users = userRepository.findAllByName("%a%");
        assertFalse(users.isEmpty());
    }

    @Test
    void updateRole() {
        User user = userRepository.getById(1L);
        assertSame(Role.ADMIN, user.getRole());
        Date newBirthDay = new Date();
        user.setBirthDate(newBirthDay); // перед updateRole flush контекста произойдет автоматически

        int count = userRepository.updateRole(Role.USER, 1L);
        assertEquals(1, count);
        //entityManager.flush(); // не помогает. PersistenceContext остается прежним. Требуется clearAutomatically = true

        //log.debug(user.getCompany().toString()); // LazyInitializationException - этого пользователя уже нет в контекстве

        user = userRepository.getById(1L);
        assertSame(Role.USER, user.getRole());
        assertSame(newBirthDay.getYear(), user.getBirthDate().getYear());
    }

}
