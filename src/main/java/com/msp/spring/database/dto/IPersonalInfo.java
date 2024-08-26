package com.msp.spring.database.dto;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface IPersonalInfo {

    String getFirstName();

    String getLastName();

    LocalDate getBirthDate();

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

}
