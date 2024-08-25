package com.msp.spring.database.repository;

import com.msp.spring.database.entity.Role;
import com.msp.spring.database.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // слишком длинное название
    //List<User> findAllByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName);

    // использование % не соответствует HQL стандарту и поддерживается только в spring
    @Query("select u from User u " +
           "where u.firstName like %:firstName% and u.lastName like %:lastName%")
    List<User> findAllBy(String firstName, String lastName);

    // параметр нужно передавать с %
    @Query(value = "SELECT u.* FROM users u " +
            "WHERE u.firstName like :firstName ",
            nativeQuery = true
            )
    List<User> findAllByName(String firstName);

    @Modifying(clearAutomatically = true, flushAutomatically = false)
    @Query("update User u " +
           "set u.role = :role " +
           "where u.id in (:ids)")
    int updateRole(Role role, Long ... ids);

    //Optional<User> findTopByOrderByIdDesc();
    Optional<User> findTopBy(Sort sort);

    //List<User> findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate dateBefore);
    List<User> findTop3ByBirthDateBefore(LocalDate dateBefore, Sort sort);

    //
    //List<User> findAllBy(Pageable pageable);
    //Slice<User> findAllBy(Pageable pageable);
    //Page<User> findAllBy(Pageable pageable);

    //меняем дефолтные запросы
    @Query(value = "select u from User u where u.id < 100",
           countQuery = "select count(distinct u.userName) from User u"     )
    Page<User> findAllBy(Pageable pageable);
}
