package com.msp.spring.integration.service;

import com.msp.spring.IT;
import com.msp.spring.database.pool.ConnectionPool;
import com.msp.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;

//@SpringBootTest
@IT
@RequiredArgsConstructor
public class UserServiceIT {

    private final UserService userService;
    /*
    Несмотря на одинаковый набор аннотацию, в этом тесте переопределяется бин
    поэтому будет создаваться новый контекст
     */
   /* @SpyBean(name="pool")
    private ConnectionPool pool;*/
    private final ApplicationContext applicationContext;

    @Test
    void findById() {
        System.out.println("UserServiceIT findById");
    }
}
