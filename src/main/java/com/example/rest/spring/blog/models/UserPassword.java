package com.example.rest.spring.blog.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "password_user")
public class UserPassword {

    @Id
    @Column(name = "password_id")
    private long passwordId;

    @NotBlank()
    @Column(name = "password", nullable = false)
    private String password;

    public long getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(long passwordId) {
        this.passwordId = passwordId;
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
                "authId=" + passwordId +
                ", password='" + password + '\'' +
                '}';
    }
}
