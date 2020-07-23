package com.example.rest.spring.blog.repositories;

import com.example.rest.spring.blog.models.UserPassword;
import org.springframework.stereotype.Repository;


public interface PasswordRepository<S extends UserPassword, ID extends Long>{
    int updatePassword(String newPassword, ID id);
    void createPassword(String newPassword, ID id);
    S findById(ID id);

}
