package com.msp.spring.web;

public class NoComponentBean {
    private String str;

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "NoComponentBean{" +
                "str='" + str + '\'' +
                '}';
    }
}
