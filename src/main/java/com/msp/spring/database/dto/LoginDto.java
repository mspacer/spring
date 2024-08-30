package com.msp.spring.database.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class LoginDto {

    public String userName;
    public String password;
    public String sometext;

}
