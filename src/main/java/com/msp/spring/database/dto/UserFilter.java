package com.msp.spring.database.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public final class UserFilter {
    String firstName;

    String lastName;

    LocalDate birthDate;

}
