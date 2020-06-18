package com.example.rest.spring.blog.service.user;

import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.service.CrudService;

import java.util.List;

public interface UserService extends CrudService<User,Long> {

    User findByEmailIgnoreCase(String email);
}
