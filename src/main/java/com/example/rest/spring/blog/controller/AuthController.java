package com.example.rest.spring.blog.controller;


import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.service.user.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getSignUp(Model model) {
        return "/auth/registration";
    }


    @PostMapping("/registration")
    public String signUp(@ModelAttribute User user,
                         @RequestParam(name = "password", required = true) String password,
                         @RequestParam(name = "password_confirmation", required = true) String confirmPassword,
                         Model model) {
        if ( !confirmPassword.equals(password)) {
            model.addAttribute("errorMessage", "Подтверждения пароля и пароль не совпадают");
            return "/auth/registration";
        }
        this.userService.registration(user, password);
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(name ="error_auth", required = false) boolean isError,
                        Model model) {
            model.addAttribute("error_auth", isError);
        return "/auth/sign_in";
    }

}
