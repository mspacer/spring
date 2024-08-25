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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        final org.hibernate.engine.spi.SessionImplementor session = entityManager.unwrap(org.hibernate.engine.spi.SessionImplementor.class);
        pc = session.getPersistenceContext();
    }


    @Test
    void checkPageable() {
        PageRequest pageable = PageRequest.of(1, 2, Sort.by("id"));
        //List<User> allBy = userRepository.findAllBy(pageable);
        //Slice<User> allBy = userRepository.findAllBy(pageable);
        Page<User> allBy = userRepository.findAllBy(pageable);

        allBy.forEach(user -> log.debug("user id = {}", user.getId()));

        //Slice/Page позволяет получить следующую страницу (offset)
        log.debug("number: {}, numberOfElements: {}", allBy.getNumber(), allBy.getNumberOfElements());
        log.debug("totalPages: {}, totalElements: {}", allBy.getTotalPages(), allBy.getTotalElements());

        //while (allBy.hasPrevious()) {
        while (allBy.hasNext()) {
            //allBy = userRepository.findAllBy(allBy.previousPageable());
            allBy = userRepository.findAllBy(allBy.nextPageable());
            allBy.forEach(user -> log.debug("user id = {}", user.getId()));
            log.debug("number: {}, numberOfElements: {}", allBy.getNumber(), allBy.getNumberOfElements());
        }


    }

    @Test
    void findByOrder() {
        //Optional<User> optionalUser = userRepository.findTopByOrderByIdDesc();
        //Sort sort = Sort.by(Sort.Order.desc("id"));
        //Sort sort = Sort.by("id").descending().and(Sort.by("firstName").ascending());
        Sort.TypedSort<User> sortBy = Sort.sort(User.class);
        Sort sort = sortBy.by(User::getId).descending().
                and(sortBy.by(User::getFirstName).ascending());

        Optional<User> optionalUser = userRepository.findTopBy(sort);
        assertFalse(optionalUser.isEmpty());
        optionalUser.ifPresent(user -> assertThat(user.getUserName()).isEqualTo("kate@gmail.com"));

        List<User> users = userRepository.findTop3ByBirthDateBefore(LocalDate.now(),
                Sort.sort(User.class).by(User::getBirthDate).descending());
        assertThat(users).isNotEmpty();

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
        LocalDate newBirthDay = LocalDate.now();
        user.setBirthDate(newBirthDay); // перед updateRole flush контекста произойдет автоматически

        int count = userRepository.updateRole(Role.USER, 1L);
        assertEquals(1, count);
        //entityManager.flush(); // не помогает. PersistenceContext остается прежним. Требуется clearAutomatically = true

        //log.debug(user.getCompany().toString()); // LazyInitializationException - этого пользователя уже нет в контекстве

        user = userRepository.getById(1L);
        assertSame(Role.USER, user.getRole());
        assertEquals(newBirthDay.getYear(), user.getBirthDate().getYear());
    }

}
