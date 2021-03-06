package com.example.rest.spring.blog.service.post;

import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.Comment;
import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.repositories.CommentRepository;
import com.example.rest.spring.blog.repositories.PostRepository;
import com.example.rest.spring.blog.service.user.UserService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements  PostService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private UserService userService;

    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UserService userService) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    public <S extends Post> S save(S post) {
        User principal = this.userService.getPrincipal();
        if (post.getId() != 0) {
            return updateAnonsAndFullText(principal, post);
        }
        if (post.getUser() == null) {
            post.setUser(principal);
        } else {
            throw new ErrorMessageForUserException("Не зарегистрированные пользователи не могут публиковать посты");
        }
        if (postRepository.existsByTitleIgnoreCase(post.getTitle())) {
            throw new ErrorMessageForUserException("Название статьй дожно быть уникальным. Введите новое название темы");
        }
        return saveNewPost(post);
    }

    private  <S extends Post> S saveNewPost(S post) {
        Date now = new Date();
        post.setCreatedPostDate(now);
        post.setChangedPostDate(now);
        return this.postRepository.save(post);
    }

    private <S extends Post> S updateAnonsAndFullText(User principal, Post post) {
        if (post.getUser() != null && post.getUser().getId() != principal.getId()) {
            throw new ErrorMessageForUserException("Вы не являетесь владельцем данного поста и не можети его изменять");
        }
        this.postRepository.updateAnonsAndFullText(post.getId(),post.getAnons(),post.getFullText(),new Date());
        return (S) findById(post.getId()).get();
    }

    @Override
    public <S extends Post> Iterable<S> saveAll(Iterable<S> iterable) {
        return this.postRepository.saveAll(iterable);
    }

    @Override
    public boolean existsById(Long id) {
        return this.postRepository.existsById(id);
    }


    @Override
    public Page findAllAndSortByParameter(int page, String parameterSort) {
        Pageable rageable = PageRequest.of(page, 3, ParameterSort.valueOf(parameterSort).getSort());
        Page pagePosts = this.postRepository.findAll(rageable);
        return pagePosts;
    }

    @Override
    public <S extends Post> S addCommentToPost(Comment comment, long postId){
        Post post = this.findById(postId).get();
        checkParameter(post);
        comment.setUser(this.userService.getPrincipal());
        comment.setCreatedDate(new Date());
        comment.setPost(post);
        Comment save = this.commentRepository.save(comment);
        post.getComments().add(save);
        return (S) post;


    }

    private void checkParameter(Post post) throws ErrorMessageForUserException {
        if ( post == null ) throw new ErrorMessageForUserException("Пост не найден");
    }



    @Override
    public Optional<Post> findById(Long id) {
        return this.postRepository.findById(id);
    }

    @Override
    public Iterable<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
    this.postRepository.deleteById(id);
    }

    @Override
    public Iterable<Post> findAllById(Iterable<Long> iterable) {
        return this.postRepository.findAllById(iterable);
    }

    @Override
    public long count() {
        return this.postRepository.count();
    }

    @Override
    public void delete(Post post) {
        this.postRepository.delete(post);
    }

    @Override
    public void deleteAll(Iterable<? extends Post> iterable) {
        this.postRepository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        this.postRepository.deleteAll();
    }

    @Override
    public void updateViews(long postID) {
            this.postRepository.updateViews(postID);
    }

    @Override
    public Iterable<Post> findByKeyword(String keyword) {
        return this.postRepository.findByKeyword(keyword);
    }

//    private User getUser(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return  (User) auth.getPrincipal();
//    }
}

