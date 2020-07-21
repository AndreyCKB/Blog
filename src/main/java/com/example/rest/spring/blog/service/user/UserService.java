package com.example.rest.spring.blog.service.user;

import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.service.SimpleService;



public interface UserService extends SimpleService<User, Long> {
    User getAuthUser();
    <S extends User> S updateUser(S user);
    void registration(User user, String password);
    void changePassword(String currentPassword, String newPassword);
    void resetPassword();
    User findByEmailIgnoreCase(String email);
    void changeEmail(String newEmail, String password) throws ErrorMessageForUserException;
}
