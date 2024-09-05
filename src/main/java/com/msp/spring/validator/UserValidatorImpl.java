package com.msp.spring.validator;

import com.msp.spring.database.dto.UserCreateEditDto;
import com.msp.spring.web.NoComponentBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Slf4j
public class UserValidatorImpl implements ConstraintValidator<UserValidator, UserCreateEditDto> {

    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        return true;
    }
}
