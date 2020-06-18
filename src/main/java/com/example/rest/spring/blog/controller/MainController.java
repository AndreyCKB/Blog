package com.example.rest.spring.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/")
    public String about() {
        return "redirect:/login";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "ABOUT");
        return "about";
    }

}