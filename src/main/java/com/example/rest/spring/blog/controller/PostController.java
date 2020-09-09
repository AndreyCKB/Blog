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
        logger.trace("listPosts(@RequestParam(value = \"page_number\", required = false, defaultValue = \"1\") int pageNumber = \"{}\",\n" +
                "                            @RequestParam(name = \"parameterSort\", required = false, defaultValue = \"TITLE\") String parameterSort = \"{}\",\n" +
                "                            Model model = = \"{}\")",pageNumber, parameterSort, model);
        return commonForListPosts(pageNumber,parameterSort, model);
    }

    @PostMapping("/list_posts/sort")
    public String listPostsSort(@RequestParam(value = "page_number", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(name = "parameterSort") String parameterSort,
                                Model model) {
        logger.trace("listPostsSort(@RequestParam(value = \"page_number\", required = false, defaultValue = \"1\") int pageNumber = \"{}\",\n" +
                "                            @RequestParam(name = \"parameterSort\") String parameterSort = \"{}\",\n" +
                "                            Model model = = \"{}\")",pageNumber, parameterSort, model);
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
            logger.debug("Base Post is empty");
            model.addAttribute("errorMessage", "База постов пуста.");
            return "/posts/post-list";
        }
        logger.debug("Count posts = \"{}\"", countPosts);
        Page page = this.postService.findAllAndSortByParameter(pageNumber - 1, parameterSort);
        if (page.isEmpty()) {
            page = this.postService.findAllAndSortByParameter(0, parameterSort);
        }
        model.addAttribute("pages", Pagination.getPages(pageNumber,countPosts));
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
            return postIDNotFound(postID, e, model);
        }
        logger.debug("Post with id = \"{}\" found", postID);
        model.addAttribute("post", htmlPost);
        return "/posts/post-details";
    }

    @GetMapping("/add")
    public String addPostPage(Model model) {
        model.addAttribute("topics", this.topicService.findAll());
        logger.debug("All topicsModel added to model.");
        return "/posts/post-add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("post")Post post, Model model) {
        if (post.getTopic() == null ){
            model.addAttribute("errorMessage", "Вы не выбрали тему для поста.");
            logger.debug("Topic not added to post");
            return  addPostPage(model);
        }
        Post save;
        try {
            save = this.postService.save(post);
        }catch (ErrorMessageForUserException e){
            logger.warn("Method addPost(@ModelAttribute(\"post\")Post post, Model model)",e);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("topicID", post.getTopic().getTopicId());
            model.addAttribute("post", post);
            return  addPostPage(model);
        }
        logger.debug("Post added to base. Post = \"{}\"", save);
        return "redirect:/post/list_posts";
    }

    @GetMapping("/{id}/edit")
    public String editPostPage(@PathVariable(value = "id") long id, Model model) {
        logger.trace("Method \"editPostPage(@PathVariable(value = \"id\") long id = \"{}\", Model model = \"{}\") started", id , model);
        try {
            model.addAttribute("post", this.postService.findById(id).get());
        }catch (RuntimeException e){
            return postIDNotFound(id, e, model);
        }
        return "/posts/post-edit";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@ModelAttribute("post")Post post) {
        logger.trace("Method \"editPost(@ModelAttribute(\"post\")Post post = \"{}\") started", post);
        Post save = this.postService.save(post);
        logger.debug("Post edit. Current post = \"{}\"", save);
        return "redirect:/post/list_posts";
    }

    @GetMapping("/{id}/delete")
    public String postDelete(@PathVariable(value = "id") long id, Model model) {
        logger.trace("blogDelete(@PathVariable(value = \"id\") long id =\"{}\") started", id);
        try {
            this.postService.deleteById(id);
        }catch (RuntimeException e){
            return postIDNotFound(id, e, model);
        }
        logger.debug("Post with id = \"{}\" deleted", id);
        return "redirect:/post/list_posts";
    }

    @PostMapping("/find_post")
    public String findPost(@RequestParam(name = "keyword",required = false) String keyword, Model model) {
        logger.trace("findPost(@RequestParam(name = \"keyword\",required = false) String keyword = \"{}\", Model model)",keyword);
        Iterable<Post> posts;
        String errorMessage = null;
        model.addAttribute("keyword" , keyword);
        if (keyword != null && !keyword.isEmpty()){
            posts = this.postService.findByKeyword(keyword);
            if (posts.iterator().hasNext() == false) {
                logger.debug("Keyword = \"{}\" not found in database", keyword);
                errorMessage = "В базе не найдено постов по ключевому слову \"" + keyword + "\"";
            } else {
                logger.debug("Posts with keyword =\"{}\" found to database.", keyword);
                model.addAttribute("posts", posts);
            }
        } else {
             logger.debug("Keyword is empty");
             errorMessage = "Вы ничего не ввели в строку поиска";
        }
        model.addAttribute("errorMessage",errorMessage);
        return "/posts/post-list";
    }

    @GetMapping("/{id}/all-comments")
    public String showComments(@PathVariable(value = "id") long id,
                               @RequestParam(value = "enable_comments") boolean enableСomments,
                               HtmlPost htmlPost,
                               Model model) {
        logger.trace("showComments(@PathVariable(value = \"id\") long id = \"{}\",\n" +
                "                               @RequestParam(value = \"enable_comments\") boolean enableСomments = \"{}\",\n" +
                "                               HtmlPost htmlPost,\n" +
                "                               Model model)", id, enableСomments);
        try {
            htmlPost.setPost(this.postService.findById(id).get());
        }catch (RuntimeException e) {
            return postIDNotFound(id, e, model);
        }
        model.addAttribute("post",htmlPost);
        logger.debug("Post with id = \"{}\" found", id);
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
        try {
            if (comment == null || comment.getMessage() == null || comment.getMessage().isEmpty()) {
                logger.debug("Message is empty in comment");
                model.addAttribute("errorMessage", "Вы не ввели текст для комментария");
                model.addAttribute("post", htmlPost.setPost(this.postService.findById(id).get()));
            } else {
                model.addAttribute("post", htmlPost.setPost(this.postService.addCommentToPost(comment, id)));
                model.addAttribute("comments", htmlPost.getComments());
                logger.debug("Message = \"{}\" added to comment (Comment.id = \"{}\").", comment.getMessage(), comment.getCommentId());
            }
        } catch (RuntimeException e) {
            return postIDNotFound(id, e, model);
        }
        return "/posts/post-details";
    }

    private String postIDNotFound(long id,Exception e, Model model){
        logger.warn("Post with id =\"{}\" not found to database.", id, e);
        model.addAttribute("errorMessage", "Пост с id = \"" + id + "\" не найден");
        return commonForListPosts(0,ParameterSort.ANONS.toString(),model);
    }

}
