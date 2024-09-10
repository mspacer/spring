package com.msp.spring.config.auth;

import com.msp.spring.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //return loadUserByUsernameDefault(username);
        return userRepository.findByUserName(username)
                .map(user ->
                        new org.springframework.security.core.userdetails.User(
                                user.getUserName(),
                                user.getPassword(),
                                Collections.singletonList(user.getRole()))
                ).orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve username: " + username));
    }

    public UserDetails loadUserByUsernameDefault(String username) throws UsernameNotFoundException {
        return  org.springframework.security.core.userdetails.User.builder()
                        .username("test@gmail.com")
                // префикс {noop} NoOpPasswordEncoder - означает, что пароль храниться "как есть"
                // Существуют моножества энкодеров
                // https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html#authentication-password-storage
                        .password("{noop}123456")
                        .roles("ADMIN")
                        .build();
    }
}
