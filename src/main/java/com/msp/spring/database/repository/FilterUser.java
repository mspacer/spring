package com.msp.spring.database.repository;

import com.msp.spring.database.dto.PersonalInfo;
import com.msp.spring.database.dto.UserFilter;
import com.msp.spring.database.entity.Role;
import com.msp.spring.database.entity.User;

import java.util.List;

public interface FilterUser {

    List<User> findAllByFilter(UserFilter filter);

    List<PersonalInfo> findAllByCompanyAndRole(Integer companyId, Role role);

    int[] updateCompanyAndRole(List<User> users);

    int[] updateCompanyAndRoleNamed(List<User> users);
}
