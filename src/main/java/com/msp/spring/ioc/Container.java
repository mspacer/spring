package com.msp.spring.ioc;

import com.msp.spring.database.pool.ConnectionPool;
import com.msp.spring.database.repository.CompanyRepository;
import com.msp.spring.database.repository.UserRepository;
import com.msp.spring.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class Container {

    private static final Map<Class<?>, Object> container = new HashMap<>();

    static {
        container.put(ConnectionPool.class, new ConnectionPool());
        container.put(UserRepository.class, new UserRepository(get(ConnectionPool.class)));
        container.put(CompanyRepository.class, new CompanyRepository(get(ConnectionPool.class)));
        container.put(UserService.class, new UserService(get(UserRepository.class), get(CompanyRepository.class)));
    }

    public static <T> T get(Class<T> clazz) {
        return (T) container.get(clazz);
    }
}
