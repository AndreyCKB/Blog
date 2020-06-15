package com.example.rest.spring.blog.service.post;


import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.service.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public interface PostService extends CrudService<Post, Long> {

    void updateAnonsAndFullText(long postID, String anons, String fullText);

    void updateViews(long postID, int views);

    Iterable<Post> findByKeyword(String keyword);

    Page findAllAndSortByParameter(int page, Sort parametrSort);
}
