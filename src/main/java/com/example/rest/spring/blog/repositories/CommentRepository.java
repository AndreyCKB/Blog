package com.example.rest.spring.blog.repositories;

import com.example.rest.spring.blog.models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
