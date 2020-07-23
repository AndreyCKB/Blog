package com.example.rest.spring.blog.service.post;

import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.Comment;
import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.repositories.CommentRepository;
import com.example.rest.spring.blog.repositories.PostRepository;
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


    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public <S extends Post> S save(S post) {
        return this.postRepository.save(post);
    }

    @Override
    public <S extends Post> Iterable<S> saveAll(Iterable<S> iterable) {
        return this.postRepository.saveAll(iterable);
    }

    @Override
    public boolean existsById(Long id) {
        return this.postRepository.existsById(id);
    }

//    @Override
//    public Page findAllAndSortByParameter(int page, Sort parameterSort) throws ErrorMessageForUserException {
//        Pageable rageable = PageRequest.of(page, 10, parameterSort);
//        Page pagePosts = this.postRepository.findAll(rageable);
//        checkParameter(pagePosts);
//        return pagePosts;
//    }
    @Override
    public Page findAllAndSortByParameter(int page, String parameterSort) throws ErrorMessageForUserException {
        Pageable rageable = PageRequest.of(page, 10, ParameterSort.valueOf(parameterSort).getSort());
        Page pagePosts = this.postRepository.findAll(rageable);
//        checkParameter(pagePosts);
        return pagePosts;
    }

    @Override
    public void addCommentToPost(Comment comment, long postId) throws ErrorMessageForUserException {
        Post post = this.findById(postId).get();
        checkParameter(post);
        comment.setPost(post);
        comment.setCreatedDate(new Date());
        Comment save = this.commentRepository.save(comment);
        post.getComments().add(save);
        this.save(post);

    }

    private void checkParameter(Post post) throws ErrorMessageForUserException {
        if ( post == null ) throw new ErrorMessageForUserException("Пользователь не найден");
    }

    private void checkParameter(Page page) throws ErrorMessageForUserException {
//        if ( page.getTotalElements() == 0 ) throw new ErrorMessageForUserException("Пользователи не найдены");
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
    public void updateAnonsAndFullText(long postID, String anons, String fullText) {
        this.postRepository.updateAnonsAndFullText(postID,anons,fullText, new Date());
    }

    @Override
    public void updateViews(long postID, int views) {
        this.postRepository.updateViews(postID,views);
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

