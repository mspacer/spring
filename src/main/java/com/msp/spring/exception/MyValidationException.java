package com.msp.spring.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import javax.validation.ValidationException;

@RequiredArgsConstructor
@Getter
public class MyValidationException extends ValidationException {
    //private final BindingResult bindingResult;
    private final Errors errors;

}
