package com.example.rest.spring.blog.controller;

import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.models.Topic;
import com.example.rest.spring.blog.repositories.TopicRepository;
import com.example.rest.spring.blog.service.topic.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/topic")
public class TopicController {

    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/add")
    public String addTopicPage() {
        return "/topics/topic-add";
    }

    @PostMapping("/add")
    public String addTopic(@ModelAttribute("topic") Topic topic, Model model) {
        topicService.save(topic);
        return "redirect:/post/list_posts";
    }

    @GetMapping("/get-topics")
    public String getTopics(Model model) {
        model.addAttribute("topics", this.topicService.findAll());
        return "/topics/topic-list";
    }
}
