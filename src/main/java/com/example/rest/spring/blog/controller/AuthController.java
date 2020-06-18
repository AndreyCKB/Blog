package com.example.rest.spring.blog.controller;

import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/registration")
    public String getSignUp(Model model) {
        return "/auth/registration";
    }

//    @Valid
    @PostMapping("/registration")
    public String signUp(@ModelAttribute  User user, BindingResult result) {
        user.setDateRegistration(new Date());
        this.userService.save(user);
        //Дописать страницу если не удалось зарегистрироваться.
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "/auth/sign_in";
    }

}
