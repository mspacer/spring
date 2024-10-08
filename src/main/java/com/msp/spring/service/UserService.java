package com.msp.spring.service;

import com.msp.spring.config.auth.MyUserDetailsService;
import com.msp.spring.database.dto.UserCreateEditDto;
import com.msp.spring.database.dto.UserFilter;
import com.msp.spring.database.dto.UserReadDto;
import com.msp.spring.database.entity.Role;
import com.msp.spring.database.entity.User;
import com.msp.spring.database.repository.UserRepository;
import com.msp.spring.database.repository.predicate.QPredicates;
import com.msp.spring.mapper.UserCreateEditMapper;
import com.msp.spring.mapper.UserReadMapper;
import com.querydsl.core.types.Predicate;
import liquibase.pro.packaged.E;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.msp.spring.database.entity.QUser.user;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final FileService fileService;
    private final MyUserDetailsService detailsService;

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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    public Optional<UserReadDto> findByName(String name) {
        return userRepository.findByUserName(name)
                .map(userReadMapper::map);
    }

    public UserDetails loadUserByUserName(String email) {
        return detailsService.loadUserByUsername(email);
        /*findByName(email)
                .map(userReadDto ->
                        new org.springframework.security.core.userdetails.User(userReadDto.getUsername(),
                                userReadDto.g,
                                Arrays.asList(userReadDto.getRole())))
                // или UsernameNotFoundException
                .orElse(new org.springframework.security.core.userdetails.User("", "", null));*/
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return userCreateEditMapper.map(dto);
                })
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto) {
        return userRepository.findById(id)
                .map(entity -> {
                    uploadImage(userDto.getImage());
                    return userCreateEditMapper.map(userDto, entity);
                })
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

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
               .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(fileService::get);
    }

    private void uploadImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                fileService.upload(image.getOriginalFilename(), image.getInputStream());
            } catch (IOException e) {
                log.error("error save file {}", image.getOriginalFilename(), e);
            }
        }
    }

}
