package com.msp.spring.config.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;

public class MyAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user = new User("test",
                "password",
                Arrays.asList(new SimpleGrantedAuthority("ADMIN")));

        return new UsernamePasswordAuthenticationToken("test",
                "password",
                Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
    }
}
