package com.msp.spring.database.repository;

import com.msp.spring.database.dto.UserFilter;
import com.msp.spring.database.entity.User;

import java.util.List;

public interface FilterUser {

    List<User> findAllByFilter(UserFilter filter);
}
