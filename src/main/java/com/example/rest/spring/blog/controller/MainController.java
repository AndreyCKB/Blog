package com.example.rest.spring.blog.controller;


import com.example.rest.spring.blog.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpSession;


@Controller
public class MainController {



    @GetMapping("/")
    public String home(HttpSession session) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) auth.getPrincipal();
//        auth.getPrincipal(.);
//        user.setPassword(null);
//        session.setAttribute("authUser", user);
        return "redirect:/blog/list";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "ABOUT");
        return "about";
    }

}