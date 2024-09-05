package com.msp.spring.validator;

import com.msp.spring.validator.group.CreationAction;
import com.msp.spring.validator.group.UpdateAction;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserValidatorImpl.class)
@UserInfo(groups = {CreationAction.class}, message = "Create error UserInfo")
@UserInfo2(groups = {UpdateAction.class}, message = "Create error UserInfo2")
public @interface UserValidator {
    String message() default "";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
