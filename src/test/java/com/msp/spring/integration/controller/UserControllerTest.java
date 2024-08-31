package com.msp.spring.integration.controller;

import com.msp.spring.IntegrationTestBase;
import com.msp.spring.database.dto.UserCreateEditDto;
import com.msp.spring.database.entity.Role;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.msp.spring.database.dto.UserCreateEditDto.Fields.birthDate;
import static com.msp.spring.database.dto.UserCreateEditDto.Fields.companyId;
import static com.msp.spring.database.dto.UserCreateEditDto.Fields.firstname;
import static com.msp.spring.database.dto.UserCreateEditDto.Fields.lastname;
import static com.msp.spring.database.dto.UserCreateEditDto.Fields.role;
import static com.msp.spring.database.dto.UserCreateEditDto.Fields.username;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attribute("users", Matchers.hasSize(5)));
    }

    @Test
    void findById() {
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
               .param(username, "test@gmail.com")
               .param(firstname, "Sveta")
               .param(lastname, "Svetikova")
               .param(role, Role.ADMIN.name())
               .param(companyId, "1")
               .param(birthDate, "2000-01-01")
        )
        .andExpectAll(status().is3xxRedirection(),
                      redirectedUrlPattern("/users/{\\d+}"));
    }

}