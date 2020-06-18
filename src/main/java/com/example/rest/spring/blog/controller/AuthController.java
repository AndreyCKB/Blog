package com.example.rest.spring.blog.controller;

import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        this.userService.save(user);
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(name ="error_auth", required = false) boolean isError,
                        Model model) {
        if (isError){
            model.addAttribute("error_auth", isError);
        }
        return "/auth/sign_in";
    }

}
