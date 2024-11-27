package com.msp.spring.service.exmpl3;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

public class ProtoService {

    private final String bankName;

    public ProtoService() {
        this.bankName = UUID.randomUUID().toString();
        System.out.println("new bank created");
    }

    public String getInput() {
        return bankName;
    }

    public Class<?> getClazz() {
        return this.getClass();
    }

}
