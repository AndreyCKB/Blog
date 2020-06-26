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
    public String signUp(@ModelAttribute  User user,
                         @RequestParam(name = "check_password", required = true) String checkPassword,
                          Model model) {
        if ( !checkPassword.equals(user.getPassword())) {
            model.addAttribute("errorMessage", "Подтверждения пароля и пароль не совпадают");
            return "/auth/registration";
        }
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
