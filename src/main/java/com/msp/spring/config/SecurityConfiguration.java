package com.msp.spring.config;

import com.msp.spring.config.auth.MyUserDetailsService2;
import com.msp.spring.database.dto.UserReadDto;
import com.msp.spring.database.entity.Role;
import com.msp.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;
import java.util.Set;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    // убирает дефолтное поведение, настроенное в SpringBootWebSecurityConfiguration
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                //если не дать права на /users/create, то при регистрации будет редирект на логин
                                .antMatchers("/login", "/logout", "/users/create", "/users/registration", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                .antMatchers("/admin/**").hasAuthority(Role.ADMIN.name())
                                .antMatchers("/users/{\\d+}/delete").hasAuthority(Role.ADMIN.name())
                                .anyRequest()
                                .authenticated())
                //.httpBasic() //для rest-контролера Basic Auth
                //.and()
                .logout(configurer ->
                        configurer // необязательно, т.к. действуют по умолчанию
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID"))
                .formLogin(configurer ->
                        configurer
                                .loginPage("/login")
                                .defaultSuccessUrl("/users"))
                .oauth2Login(configurer ->
                        configurer
                                .loginPage("/login")
                                .defaultSuccessUrl("/users")
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig
                                                .oidcUserService(userRequest -> {
                                                    final OidcUserService delegate = new OidcUserService();
                                                    OidcUser oidcUser = delegate.loadUser(userRequest);
                                                    String email = userRequest.getIdToken().getClaims().get("email").toString();
                                                    UserDetails userDetails = userService.loadUserByUserName(email);

                                                    //Этот пользователь будет лежать в SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                                                    // Но это не устраивает, т.к. приложение работает с UserDetails (fragment.html)
                                                    // поэтому нужно возвращать прокси объект

                                                    DefaultOidcUser defaultOidcUser = new DefaultOidcUser(userDetails.getAuthorities(),
                                                            userRequest.getIdToken(),
                                                            oidcUser.getUserInfo());

                                                    Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());

                                                    return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                                                            new Class[] {UserDetails.class, OidcUser.class},
                                                            (proxy, method, args) ->  userDetailsMethods.contains(method)
                                                                ? method.invoke(userDetails, args)
                                                                : method.invoke(defaultOidcUser, args));
                                                })
                                )
                );
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
