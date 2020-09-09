package com.example.rest.spring.blog.controller;

import com.example.rest.spring.blog.controller.pagination.Pagination;
import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.Comment;
import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.models.wrapper.entitys.HtmlPost;
import com.example.rest.spring.blog.service.post.ParameterSort;
import com.example.rest.spring.blog.service.post.PostService;
import com.example.rest.spring.blog.service.topic.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/post")
public class PostController {

    public static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private PostService postService;
    private TopicService topicService;

    public PostController(PostService postService, TopicService topicService) {
        this.postService = postService;
        this.topicService = topicService;
    }

    @GetMapping("/list_posts")
    public String listPosts(@RequestParam(value = "page_number", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(name = "parameterSort", required = false, defaultValue = "TITLE") String parameterSort,
                            Model model) {
        return commonForListPosts(pageNumber,parameterSort, model);
    }


    @PostMapping("/list_posts/sort")
    public String listPostsSort(@RequestParam(value = "page_number", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(name = "parameterSort") String parameterSort,
                                Model model) {
        return commonForListPosts(pageNumber,parameterSort, model);
    }

    private String commonForListPosts(int pageNumber,
                                      String parameterSort,
                                      Model model){
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        long countPosts = this.postService.count();
        if (countPosts == 0) {
            model.addAttribute("errorMessage", "База постов пуста.");
            return "/posts/post-list";
        }
        Page page = this.postService.findAllAndSortByParameter(pageNumber - 1, parameterSort);
        if (page.isEmpty()) {
            page = this.postService.findAllAndSortByParameter(0, parameterSort);
        }
        model.addAttribute("pages", Pagination.getPages(pageNumber,countPosts, 3));
        model.addAttribute("posts", page.iterator());
        model.addAttribute("paramSort", ParameterSort.valueOf(parameterSort));
        model.addAttribute("typeSort", ParameterSort.values());
        return "/posts/post-list";
    }



    //    несколько параметров указываются через / На пример ("/blog/{id}/{newParam}")
    @GetMapping("/{id}")
    public String blogDetails(@PathVariable(value = "id") long postID, HtmlPost htmlPost, Model model) {
        try {
            this.postService.updateViews(postID);
            htmlPost.setPost(this.postService.findById(postID).get());
        }catch (Exception e){
            model.addAttribute("errorMessage", "В базе не найдено поста с таким id ( id = " + postID);
            return "redirect:/post/list_posts";
        }
        model.addAttribute("post", htmlPost);
        return "/posts/post-details";
    }

    @GetMapping("/add")
    public String addPostPage(Model model) {
        model.addAttribute("topics", this.topicService.findAll());
        return "/posts/post-add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("post")Post post, Model model) {
        if (post.getTopic() == null ){
            model.addAttribute("errorMessage", "Вы не выбрали тему для поста.");
            model.addAttribute("topics", this.topicService.findAll());
            model.addAttribute("post", post);
            return "/posts/post-add";
        }
        try {
            this.postService.save(post);
        }catch (ErrorMessageForUserException e){
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("topicID", post.getTopic().getTopicId());
            model.addAttribute("topics", this.topicService.findAll());
            model.addAttribute("post", post);
            return "/posts/post-add";
        }
        return "redirect:/post/list_posts";
    }


    @GetMapping("/{id}/edit")
    public String editPostPage(@PathVariable(value = "id") long id, Model model) {
        logger.trace("Method \"editPostPage(@PathVariable(value = \"id\") long id = \"{}\", Model model = \"{}\") started", id , model);
        try {
            model.addAttribute("post", this.postService.findById(id).get());
        }catch (RuntimeException e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "В базе не найдено поста с таким id ( id = " + id);
            return "redirect:/post/list_posts";
        }
        return "/posts/post-edit";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@ModelAttribute("post")Post post) {
        logger.trace("Method \"editPost(@ModelAttribute(\"post\")Post post = \"{}\") started", post);
        this.postService.save(post);
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
                               @RequestParam(value = "enable_comments") boolean enableСomments,
                               HtmlPost htmlPost,
                               Model model) {
        htmlPost.setPost(this.postService.findById(id).get());
        model.addAttribute("post",htmlPost);
        if (enableСomments){
            model.addAttribute("comments", htmlPost.getComments());
        }
        return "/posts/post-details";
    }

    @PostMapping("/{id}/add-comment")
    public String addComment(@PathVariable(value = "id") long id,
                             @ModelAttribute(name = "comment") Comment comment,
                             HtmlPost htmlPost,
                             Model model) {
        logger.trace("Method addComment(@PathVariable(value = \"id\") long id = \"{}\",\n" +
                "                             @ModelAttribute(name = \"comment\") Comment comment = \"{}\",\n" +
                "                             HtmlPost htmlPost = \"{}\",\n" +
                "                             Model model = \"{}\") started", id, comment, htmlPost, model);
        if( comment == null || comment.getMessage() == null || comment.getMessage().isEmpty()){
            model.addAttribute("errorMessage", "Вы не ввели текст для комментария");
            model.addAttribute("post",  htmlPost.setPost(this.postService.findById(id).get()));
        }else {
            model.addAttribute("post",  htmlPost.setPost(this.postService.addCommentToPost(comment, id)));
            model.addAttribute("comments", htmlPost.getComments());
        }
        logger.trace("Model = \"{}\" ", model);
        return "/posts/post-details";
    }

}
