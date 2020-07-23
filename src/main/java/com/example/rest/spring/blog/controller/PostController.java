package com.example.rest.spring.blog.controller;

import com.example.rest.spring.blog.models.Comment;
import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.service.post.ParameterSort;
import com.example.rest.spring.blog.service.post.PostService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/list_posts")
    public String listPosts(Model model) {
        Iterable<Post> posts = this.postService.findAllAndSortByParameter(0, ParameterSort.ANONS.name());
        String errorMessage = null;
        if ( ((Page<Post>) posts).getTotalElements() == 0 ) {
            errorMessage = "База постов пуста.";
        }
        this.addPostsInModelForList(posts,model,errorMessage);
        return "/posts/post-list";
    }

    @PostMapping("/list_posts")
    public String blogWithSort(@RequestParam(name = "parameterSort",required = false) String parameterSort,Model model) {
        Iterable<Post> posts = this.postService.findAllAndSortByParameter(0, parameterSort);
        String errorMessage = null;
        if ( ((Page<Post>) posts).getTotalElements() == 0 ) {
            errorMessage = "База постов пуста.";
        }
        this.addPostsInModelForList(posts,model,errorMessage);
        return "/posts/post-list";
    }


    //    несколько параметров указываются через / На пример ("/blog/{id}/{newParam}")
    @GetMapping("/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        Post post;
        try {
            post = this.postService.findById(id).get();
            this.postService.updateViews(post.getId(), post.getViews() + 1);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "В базе не найдено поста с таким id ( id = " + id);
            return "redirect:/post/list_posts";
        }
        model.addAttribute("post", post);
        return "/posts/post-details";
    }

    @GetMapping("/add")
    public String addPostPage(Model model) {
        return "/posts/post-add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("post")Post post, Model model) {
        Date now = new Date();
        post.setCreatedPostDate(now);
        post.setChangedPostDate(now);
        this.postService.save(post);
        return "redirect:/post/list_posts";
    }


    @GetMapping("/{id}/edit")
    public String editPostPage(@PathVariable(value = "id") long id, Model model) {
        try {
            Post post = this.postService.findById(id).get();
            model.addAttribute("post", post);
        }catch (RuntimeException e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "В базе не найдено поста с таким id ( id = " + id);
            return "redirect:/post/list_posts";
        }
        return "/posts/post-edit";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@ModelAttribute("post")Post post) {
        this.postService.updateAnonsAndFullText(post.getId(),post.getAnons(),post.getFullText());
        return "redirect:/post/list_posts";
    }

    @GetMapping("/{id}/delete")
    public String blogDelete(@PathVariable(value = "id") long id) {
        this.postService.deleteById(id);
        return "redirect:/post/list_posts";
    }

    @PostMapping("/find_post")
    public String findPost(@RequestParam(name = "keyword",required = false) String keyword, Model model) {
        Iterable<Post> posts = new ArrayList<>(0);
        String errorMessage = null;
        model.addAttribute("keyword" , keyword);
        if (keyword != null && !keyword.isEmpty() ){
            posts = this.postService.findByKeyword(keyword);
            if (posts.iterator().hasNext() == false) {
                errorMessage = "В базе не найдено постов по вашему запросу " ;
            }
        } else {
             errorMessage = "Вы ничего не ввели в строку поиска" ;
        }
        this.addPostsInModelForList(posts,model,errorMessage);
        return "/posts/post-list";
    }

    private void addPostsInModelForList(Iterable<Post> posts, Model model, String errorMessage){
        model.addAttribute("posts", posts);
        model.addAttribute("typeSort", ParameterSort.values());
        model.addAttribute("errorMessage", errorMessage);
    }

    @GetMapping("/{id}/all-comments")
    public String showComments(@PathVariable(value = "id") long id,
                               Model model) {
        Post post = this.postService.findById(id).get();
        List<Comment> comments = post.getComments();
        model.addAttribute("post",post);
        model.addAttribute("comments", comments);
        return "/posts/post-details";
    }

    @PostMapping("/{id}/add-comment")
    public String addComment(@PathVariable(value = "id") long id,
                             @ModelAttribute(name = "comment") Comment comment,
                             Model model) {
        this.postService.addCommentToPost(comment, id);
        return this.blogDetails(id, model);
    }

}
