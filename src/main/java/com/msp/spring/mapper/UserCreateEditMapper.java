package com.msp.spring.mapper;

import com.msp.spring.database.dto.UserCreateEditDto;
import com.msp.spring.database.entity.Company;
import com.msp.spring.database.entity.User;
import com.msp.spring.database.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final CompanyRepository companyRepository;

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);

        return user;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setUserName(object.getUsername());
        user.setFirstName(object.getFirstname());
        user.setLastName(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
        user.setCompany(getCompany(object.getCompanyId()));

        // в операции update пароля нет
        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(s -> PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(s))
                .ifPresent(user::setPassword);

        Optional.ofNullable(object.getImage())
                //.filter(multipartFile -> !multipartFile.isEmpty())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(multipartFile -> user.setImage(multipartFile.getOriginalFilename()));
    }

    public Company getCompany(Integer companyId) {
        return Optional.ofNullable(companyId)
                .flatMap(companyRepository::findById)
                .orElse(null);
    }
}
