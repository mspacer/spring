package com.msp.spring.integration.database.repository;

import com.msp.spring.IT;
import com.msp.spring.database.dto.IPersonalInfo;
import com.msp.spring.database.dto.PersonalInfo;
import com.msp.spring.database.dto.PersonalInfo2;
import com.msp.spring.database.dto.UserFilter;
import com.msp.spring.database.entity.Role;
import com.msp.spring.database.entity.User;
import com.msp.spring.database.repository.UserRepository;
import com.msp.spring.database.repository.predicate.QPredicates;
import com.querydsl.core.types.Predicate;
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
import org.springframework.data.history.Revisions;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.msp.spring.database.entity.QUser.user;
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
    void checkJdbcBatchNamed() {
        List<User> users = userRepository.findAll();

        int[] ints = userRepository.updateCompanyAndRoleNamed(users);

        assertThat(ints).hasSize(users.size());
    }

    @Test
    void checkJdbcBatch() {
        List<User> users = userRepository.findAll();

        int[] ints = userRepository.updateCompanyAndRole(users);

        assertThat(ints).hasSize(users.size());
    }

    @Test
    void checkJdbcTemplate() {
        List<PersonalInfo> personalInfos = userRepository.findAllByCompanyAndRole(3, Role.ADMIN);
        assertThat(personalInfos).isNotEmpty();
    }

    @Test
    void checkRevision() {
        Revisions<Integer, User> revisions = userRepository.findRevisions(1l);
        System.out.println();
    }

    @Test
    @Commit
    void checkAuditing() {
        User user = userRepository.findById(1L).get();
        user.setBirthDate(user.getBirthDate().plusYears(1l));
        entityManager.flush();
        System.out.println();
    }

    @Test
    void checkCustomRepositoryImplementation() {
        UserFilter filter = new UserFilter("%et%", "ov", LocalDate.now());

        List<User> allByFilter = userRepository.findAllByFilter(filter);

        assertThat(allByFilter).isNotEmpty();

        Predicate predicate = QPredicates.builder()
                .add("%et%", user.firstName::like)
                .add("ov", user.lastName::containsIgnoreCase)
                .add(LocalDate.now(), user.birthDate::before)
                .build();

        // из QuerydslPredicateExecutor
        Iterable<User> users = userRepository.findAll(predicate);

        assertThat(users).isNotEmpty();

    }

    @Test
    void checkProjection() {
        //List<PersonalInfo> users = userRepository.findAllByCompanyId(1);
        List<PersonalInfo> users = userRepository.findAllByCompanyId(1, PersonalInfo.class);
        assertThat(users).isNotEmpty();

        List<PersonalInfo2> users2 = userRepository.findAllByCompanyId(2, PersonalInfo2.class);
        assertThat(users2).isNotEmpty();

        List<IPersonalInfo> users3 = userRepository.findUsersByCompanyId(3);
        assertThat(users3).isNotEmpty();

    }

    @Test
    void checkPageable() {
        PageRequest pageable = PageRequest.of(1, 2, Sort.by("id"));
        //List<User> allBy = userRepository.findAllBy(pageable);
        //Slice<User> allBy = userRepository.findAllBy(pageable);
        Page<User> allBy = userRepository.findAllBy(pageable);

        allBy.forEach(user -> log.debug("user company = {}", user.getCompany().getName()));

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
