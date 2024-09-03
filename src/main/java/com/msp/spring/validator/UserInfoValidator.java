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
public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateEditDto> {

    private final NoComponentBean noComponentBean;

    @Override
    public void initialize(UserInfo constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        log.info("preview: {}", noComponentBean.toString());
        noComponentBean.setStr(value.getFirstname() + "/" + value.getLastname());
        return StringUtils.hasText(value.getFirstname()) || StringUtils  .hasText(value.getLastname()) ;
    }
}
