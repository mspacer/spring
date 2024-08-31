package com.msp.spring.database.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class CompanyReadDto {

    public int id;
    public String name;

}
