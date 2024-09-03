package com.msp.spring.database.dto;

import com.msp.spring.database.entity.Role;
import com.msp.spring.validator.UserInfo;
import com.msp.spring.validator.group.CreationAction;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@FieldNameConstants
@UserInfo(groups = {CreationAction.class})
public class UserCreateEditDto {
    @NotNull
    @NotBlank
    @Email
    String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")// html input date отправляет дату в формате iso
    LocalDate birthDate;

    @Size(min = 4, max = 10, message = "размер поля firstname должен находиться в диапазоне от 4 до 10")
    String firstname;

    @Size(min = 2, max = 6)
    String lastname;

    Role role;

    Integer companyId;
}
