package com.msp.spring.config;

import com.msp.spring.config.auth.MyUserDetailsService2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    // убирает дефолтное поведение, настроенное в SpringBootWebSecurityConfiguration
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin(configurer ->
                        configurer
                                .loginPage("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll());
    }

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService2();
    }
*/

/*
    @Bean
    //public ProviderManager(List<AuthenticationProvider> providers, AuthenticationManager parent)
    // parent - будет этот менеджер
    public AuthenticationManager authenticationManager() {
        return new MyAuthenticationManager();
    }
*/
}
