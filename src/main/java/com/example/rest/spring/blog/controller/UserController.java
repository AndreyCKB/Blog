package com.example.rest.spring.blog.controller;

import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.service.user.UserService;
import com.example.rest.spring.blog.util.DateConverter;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/user")
public class UserController {

    final private DateConverter dateConverter;
    final private UserService userService;


    public UserController(DateConverter dateConverter, UserService userService) {
        this.dateConverter = dateConverter;
        this.userService = userService;

    }

    @GetMapping("/profile")
    public String profile(Authentication auth, Model model) {
        User user = (User) auth.getPrincipal();
        addUserInModel( model, this.userService.findById(user.getId()).get() );
        return "users/profile";
    }

    @PostMapping("/profile")
    public String changeProfile(@ModelAttribute("user") User user,
                                @RequestParam(name = "birthday_str", required = false) String birthday,
                                Model model) {
        user.setBirthday( this.dateConverter.stringInFormat_yyyy_MM_dd_toDate(birthday) );
        User savedUser =  this.userService.save(user);
        this.addUserInModel(model, savedUser);
        return "users/profile";
    }

    @GetMapping("/change-email")
    public String changeEmailPage(Authentication auth, Model model) {
        System.out.println(auth.getPrincipal());
        model.addAttribute("user", auth.getPrincipal());
        return "users/change-email";
    }

    @PostMapping("/change-email")
    public String changeEmail(@RequestParam(name = "new_email", required = true) String newEmail,
                              @RequestParam(name = "password", required = true) String password,
                              Authentication auth,
                              Model model) {
        model.addAttribute("user", auth.getPrincipal());
        try {
            this.userService.changeEmail((User) auth.getPrincipal(), newEmail, password);
        } catch (ErrorMessageForUserException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "users/change-email";
        }
        return "redirect:/logout";
    }

    @GetMapping("/change-password")
    public String changePasswordPage(Authentication auth, Model model) {
        model.addAttribute("user", auth.getPrincipal());
        return "users/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam(name = "password", required = true) String currentPassword,
                                 @RequestParam(name = "new_password", required = true) String newPassword,
                                 @RequestParam(name = "check_password", required = true) String checkPassword,
                                 Authentication auth,
                                 Model model) {
        model.addAttribute("user", auth.getPrincipal());
        if ( !newPassword.equals(checkPassword) ) {
            model.addAttribute("errorMessage", "Подтверждения нового пароля и новый пароль не совпадают");
            return "users/change-password";
            }
        try {
            this.userService.changePassword((User) auth.getPrincipal(),currentPassword, newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "users/change-password";
        }
        return "redirect:/logout";
    }

    private void addUserInModel(Model model, User user){
        model.addAttribute("user", user);
        model.addAttribute("birthday" , dateConverter.dateToStringInFormat_yyyy_MM_dd(user.getBirthday()));
    }





}
