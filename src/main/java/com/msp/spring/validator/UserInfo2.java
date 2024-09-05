package com.msp.spring.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserInfoValidator2.class)
public @interface UserInfo2 {

    String message() default "UserInfo2: firstname и lastname не должны быть пустыми";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
