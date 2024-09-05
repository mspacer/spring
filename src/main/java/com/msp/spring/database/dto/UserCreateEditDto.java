package com.msp.spring.database.dto;

import com.msp.spring.database.entity.Role;
import com.msp.spring.validator.*;
import com.msp.spring.validator.group.CreationAction;
import com.msp.spring.validator.group.UpdateAction;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@FieldNameConstants
//@UserInfo(groups = {CreationAction.class})
@UserValidator(groups = {CreationAction.class})
@UserValidatorUpdate(groups = {UpdateAction.class})
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "firstname",
                fieldMatch = {"companyId", "birthDate"},
                message = "Passwords do not match!"
        ),
        @FieldsValueMatch(
                field = "username",
                fieldMatch = "companyId",
                message = "Email addresses do not match!"
        )
})
public class UserCreateEditDto {
    @NotNull
    @NotBlank
    @Email
    String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")// html input date отправляет дату в формате iso
    LocalDate birthDate;

    @UserName(groups = {CreationAction.class}, message = "message for firstname")
    @Size(min = 4, max = 10, message = "размер поля firstname должен находиться в диапазоне от 4 до 10")
    String firstname;

    @UserName(groups = {CreationAction.class}, message = "message for lastname")
    @Size(min = 6, max = 16)
    String lastname;

    Role role;

    Integer companyId;

    MultipartFile image;
}
