package com.msp.spring.service.exmpl3;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

public class SingleService {

    @Autowired
    ProtoService protoService;

    @Autowired
    Supplier<ProtoService> protoServiceSupplier;

    public String generateUUID() {
        return protoService.getInput();
    }

    public Class<?> getAutowiredProtoClass() {
        return protoService.getClass();
    }
    public Class<?> getProtoClass() {
        return protoService.getClazz();
    }

    public String generateSupplierUUID() {
        return protoServiceSupplier.get().getInput();
    }

}