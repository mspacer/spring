package com.msp.spring.web.rest;

import com.msp.spring.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserRestControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    private static final MockMultipartFile A_FILE = new MockMultipartFile("files", "instruction",
                                            "application/octet-stream",
                                                       "Employee Record".getBytes());

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
}