package com.msp.spring.validator;

import com.msp.spring.database.dto.UserCreateEditDto;
import com.msp.spring.web.NoComponentBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Slf4j
public class UserInfoValidator2 implements ConstraintValidator<UserInfo2, UserCreateEditDto> {

    @Override
    public void initialize(UserInfo2 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        log.info("UserInfoValidator2 isValid()");
        return false;//StringUtils.hasText(value.getFirstname()) || StringUtils  .hasText(value.getLastname()) ;
    }
}
