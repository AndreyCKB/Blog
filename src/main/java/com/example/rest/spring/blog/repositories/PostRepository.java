package com.example.rest.spring.blog.repositories;

import com.example.rest.spring.blog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface PostRepository extends CrudRepository<Post, Long>, PagingAndSortingRepository<Post, Long> {

    @Override
    Iterable findAll(Sort sort);

    @Override
    Page findAll(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("FROM Post p WHERE p.anons LIKE '%' || :keyword  || '%'"
            + " OR p.title LIKE '%' || :keyword  || '%'"
            + " OR p.fullText LIKE '%' || :keyword  || '%'")
    Iterable<Post> findByKeyword( @Param("keyword")  String keyword);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.views = :views WHERE p.id = :postID")
    void updateViews(@Param("postID") long postID, @Param("views") int views);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.anons = :anons, p.fullText = :fullText, p.changedPostDate = :changedPostDate WHERE p.id = :postID")
    void updateAnonsAndFullText(@Param("postID") long postID, @Param("anons")  String anons,
                                @Param("fullText")  String fullText, @Param("changedPostDate")Date changeDate);

}
