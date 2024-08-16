package com.msp.spring.database.repository;

import com.msp.spring.database.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @Qualifier("pool")
    private final ConnectionPool pool;

    @Override
    public String toString() {
        return "UserRepository class initialized.";
    }

}
