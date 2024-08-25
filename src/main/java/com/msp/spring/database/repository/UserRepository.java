package com.msp.spring.database.repository;

import com.msp.spring.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

}
