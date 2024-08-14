package com.msp.spring.database.dto;

public class CompanyReadDto {

    public int id;

    public CompanyReadDto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CompanyReadDto{" +
                "id=" + id +
                '}';
    }
}
