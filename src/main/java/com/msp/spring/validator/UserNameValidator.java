package com.msp.spring.validator;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class UserNameValidator implements ConstraintValidator<UserName, String> {
    @Override
    public void initialize(UserName constraintAnnotation) {
        //constraintAnnotation.
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        ConstraintValidatorContextImpl validatorContext = (ConstraintValidatorContextImpl)context;//.unwrap(ConstraintValidatorContextImpl.class);
        ConstraintViolationCreationContext constraintViolationCreationContext = validatorContext.getConstraintViolationCreationContexts().get(0);
        Object testExpression = constraintViolationCreationContext.getExpressionVariables().get("test");
        if (testExpression == null) {
            validatorContext.addExpressionVariable("test", "test str");
        } else {
            log.info("expression test: {}", testExpression);
        }
        // переопределяеть дефолтный месседж
        context.disableDefaultConstraintViolation();
        String template = context.getDefaultConstraintMessageTemplate();
        context.buildConstraintViolationWithTemplate(template +  " (changed)")
                .addConstraintViolation();
        return false;
    }
}
