package com.example.rest.spring.blog.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "auth_user")
public class AuthorizedUser {

    @Id
    @Column(name = "auth_id")
    private long authId;

    @NotBlank()
    @Column(name = "password", nullable = false)
    private String password;

    public long getAuthId() {
        return authId;
    }

    public void setAuthId(long authId) {
        this.authId = authId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthorizedUser{" +
                "authId=" + authId +
                ", password='" + password + '\'' +
                '}';
    }
}
