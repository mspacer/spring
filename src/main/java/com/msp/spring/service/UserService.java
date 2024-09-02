package com.msp.spring.service;

import com.msp.spring.database.dto.UserCreateEditDto;
import com.msp.spring.database.dto.UserFilter;
import com.msp.spring.database.dto.UserReadDto;
import com.msp.spring.database.repository.UserRepository;
import com.msp.spring.database.repository.predicate.QPredicates;
import com.msp.spring.mapper.UserCreateEditMapper;
import com.msp.spring.mapper.UserReadMapper;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.msp.spring.database.entity.QUser.user;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public List<UserReadDto> findAll(UserFilter filter) {
        return userRepository.findAllByFilter(filter).stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        Predicate predicate = QPredicates.builder()
                .add(filter.getFirstName(), user.firstName::like)
                .add(filter.getLastName(), user.lastName::containsIgnoreCase)
                .add(filter.getBirthDate(), user.birthDate::before)
                .build();

        return userRepository.findAll(predicate, pageable)
                .map(userReadMapper::map);
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto) {
        return userRepository.findById(id)
                .map(entity -> userCreateEditMapper.map(userDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
