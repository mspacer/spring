package com.msp.spring.integration.database.repository;

import com.msp.spring.IT;
import com.msp.spring.database.entity.User;
import com.msp.spring.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@IT
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void findByName() {
        List<User> users = userRepository.findAllBy("a", "ov");
        assertFalse(users.isEmpty());

        users = userRepository.findAllByName("%a%");
        assertFalse(users.isEmpty());
    }

}
