package com.msp.spring.integration.controller;

import com.msp.spring.IntegrationTestBase;
import com.msp.spring.database.entity.Role;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Arrays;
import java.util.List;

import static com.msp.spring.database.dto.UserCreateEditDto.Fields.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @BeforeEach
    void init() {
        // Первый вариант
/*
        List<GrantedAuthority> authorities = Arrays.asList(Role.ADMIN, Role.USER);
        User testUser = new User("test", "test", authorities);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(testUser, testUser.getPassword(), authorities);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(testingAuthenticationToken);
        SecurityContextHolder.setContext(context);
*/
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users")
                        /*.with(SecurityMockMvcRequestPostProcessors.user(new User("test", "test", Arrays.asList(Role.ADMIN, Role.USER))))*/
                        .with(SecurityMockMvcRequestPostProcessors.user("test").password("test").authorities(Role.ADMIN, Role.USER)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                /*.andExpect(model().attribute("users", Matchers.hasSize(1)))*/;
    }

    @Test
    void findById() {
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(username, "test@gmail.com")
                        .param(rawPassword, "123")
                        .param(firstname, "Sveta")
                        .param(lastname, "Svetikova")
                        .param(role, Role.ADMIN.name())
                        .param(companyId, "1")
                        .param(birthDate, "2000-01-01")
                        .param(image, String.valueOf(new MockMultipartFile("image", new byte[0])))
                )
                .andExpectAll(status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}"));
    }

}