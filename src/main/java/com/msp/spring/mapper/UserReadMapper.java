package com.msp.spring.mapper;

import com.msp.spring.database.dto.CompanyReadDto;
import com.msp.spring.database.dto.UserReadDto;
import com.msp.spring.database.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto map(User object) {
        CompanyReadDto company = Optional.ofNullable(object.getCompany())
                .map(companyReadMapper::map)
                .orElse(null);
        return new UserReadDto(
                object.getId(),
                object.getUserName(),
                object.getBirthDate(),
                object.getFirstName(),
                object.getLastName(),
                object.getRole(),
                company,
                object.getImage()
        );
    }
}
