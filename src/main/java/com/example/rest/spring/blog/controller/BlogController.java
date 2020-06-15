package com.example.rest.spring.blog.controller;

import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.service.post.ParametrSort;
import com.example.rest.spring.blog.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private PostService postService;

    @Autowired
    public BlogController(PostService postService) {
        this.postService = postService;
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public String blog(Model model) {
        Iterable<Post> posts = this.postService.findAllAndSortByParameter(0, ParametrSort.TITLE.getSort());
        this.addPostsInModel(posts,model);
        return "blog-main";
    }

    @PostMapping("/list")
    public String blogWithSort(@RequestParam(name = "parametrSort",required = false) String parametrSort,Model model) {
        Iterable<Post> posts = this.postService.findAllAndSortByParameter(0, ParametrSort.valueOf(parametrSort).getSort());
        this.addPostsInModel(posts,model);
        return "blog-main";
    }

    private void addPostsInModel(Iterable<Post> posts, Model model){
        model.addAttribute("posts", posts);
        model.addAttribute("typeSort", ParametrSort.values());
    }
    //    несколько параметров указываются через / На пример ("/blog/{id}/{newParam}")
    @GetMapping("/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        Post post;
        try {
            post = this.postService.findById(id).get();
            this.postService.updateViews(post.getId(), post.getViews()+1);
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/blog/list";
        }
        model.addAttribute("post", post);
        return "blog-details";
    }

    @GetMapping("/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/add")
    public String blogAddPost(@ModelAttribute("post")Post post, Model model) {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        post.setCreatedPostDate(now);
        post.setChangedPostDate(now);
        this.postService.save(post);
        return "redirect:/blog/list";
    }


    @GetMapping("/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        try {
            Post post = this.postService.findById(id).get();
            model.addAttribute("post", post);
        }catch (RuntimeException e){
            return "redirect:/blog/list";
        }
        return "blog-edit";
    }

    @PostMapping("/{id}/edit")
    public String blogEditPost(@ModelAttribute("post")Post post, Model model) {
        this.postService.updateAnonsAndFullText(post.getId(),post.getAnons(),post.getFullText());
        return "redirect:/blog/list";
    }

    @GetMapping("/{id}/delete")
    public String blogDelete(@PathVariable(value = "id") long id, Model model) {
        this.postService.deleteById(id);
        return "redirect:/blog/list";
    }

    @PostMapping("/find")
    public String findPost(@RequestParam(name = "keyword",required = false) String keyword, Model model) {
        Iterable<Post> posts;
        if (keyword != null && !keyword.isEmpty() ){
            posts = this.postService.findByKeyword(keyword);
        }else {
            return "redirect:/blog/list";
        }
        this.addPostsInModel(posts,model);
        return "blog-main";
    }

}
