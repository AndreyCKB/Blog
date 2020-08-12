package com.example.rest.spring.blog.service.post;


import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.Comment;
import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.service.ExtendedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface PostService extends ExtendedService<Post, Long> {

//    void updateAnonsAndFullText(long postID, String anons, String fullText);

    void updateViews(long postID);

    Iterable<Post> findByKeyword(String keyword);

    Page findAllAndSortByParameter(int page, String parameterSort) throws ErrorMessageForUserException;

    <S extends Post> S addCommentToPost(Comment comment, long postId) throws ErrorMessageForUserException;



}
