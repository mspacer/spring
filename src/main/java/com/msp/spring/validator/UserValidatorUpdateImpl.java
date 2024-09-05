package com.msp.spring.validator;

import com.msp.spring.database.dto.UserCreateEditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Slf4j
public class UserValidatorUpdateImpl implements ConstraintValidator<UserValidatorUpdate, UserCreateEditDto> {

    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        log.info("UserValidatorUpdateImpl isValid()");
        return true;
    }
}
