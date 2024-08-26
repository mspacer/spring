package com.msp.spring.database.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@AllArgsConstructor
public class PersonalInfo {

    String firstName;

    String lastName;

    LocalDate birthDate;

}
