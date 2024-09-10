package com.msp.spring.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService2 implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
