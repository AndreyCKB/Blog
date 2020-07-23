package com.example.rest.spring.blog.repositories;

import com.example.rest.spring.blog.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long>, JpaRepository<Topic, Long> {

//    @Query("SELECT COUNT(*) FROM Topic t WHERE t.name = ?1")
    boolean existsTopicByName(String name);
}
