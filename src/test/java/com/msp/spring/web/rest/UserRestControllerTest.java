package com.msp.spring.web.rest;

import com.msp.spring.IntegrationTestBase;
import com.msp.spring.database.entity.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserRestControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    private static final MockMultipartFile A_FILE = new MockMultipartFile("files", "instruction",
                                            "application/octet-stream",
                                                       "Employee Record".getBytes());

    @BeforeEach
    void init() {
        List<GrantedAuthority> authorities = Arrays.asList(Role.ADMIN, Role.USER);
        User testUser = new User("test", "test", authorities);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(testUser, testUser.getPassword(), authorities);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(testingAuthenticationToken);
        SecurityContextHolder.setContext(context);
    }

    @Test
    void create() throws Exception {
        MockMultipartFile employeeJson = new MockMultipartFile("model", null,
                "application/json",
                ("{\"username\": \"vasia@gmail.com\"," +
                 "\"birthDate\": \"2024-09-05\","+
                 "\"firstname\": \"Vasia\"," +
                 "\"lastname\": \"Vvasia\"," +
                 "\"role\": \"USER\"," +
                 "\"companyId\": 1 }")
                        .getBytes());

        mockMvc.perform(multipart("/api/v1/users/multi")
                        .file(A_FILE)
                        .file(A_FILE)
                        .file(employeeJson))
                .andExpect(status().isOk());
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }

}