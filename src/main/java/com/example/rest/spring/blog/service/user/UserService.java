package com.example.rest.spring.blog.service.user;

import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.service.SimpleService;
import org.springframework.security.core.Authentication;

public interface UserService extends SimpleService<User,Long> {

    User findByEmailIgnoreCase(String email);

    void changeEmail(User user, String newEmail, String password) throws ErrorMessageForUserException;

    void changePassword(User user, String currentPassword, String newPassword) throws ErrorMessageForUserException;

    void resetPassword(Authentication auth);
}
