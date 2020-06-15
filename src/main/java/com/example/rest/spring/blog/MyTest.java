package com.example.rest.spring.blog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyTest {
    @Value("${test: default}")
    private String st;

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    @Override
    public String toString() {
        return "MyTest{" +
                "st='" + st + '\'' +
                '}';
    }
}
