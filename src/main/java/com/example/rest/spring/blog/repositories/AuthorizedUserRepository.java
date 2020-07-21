package com.example.rest.spring.blog.repositories;

import com.example.rest.spring.blog.models.AuthorizedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AuthorizedUserRepository extends CrudRepository<AuthorizedUser, Long>, JpaRepository<AuthorizedUser, Long> {

    @Modifying
    @Query("UPDATE AuthorizedUser au SET au.password = ?1 WHERE au.id = ?2")
    int updatePassword(String newPassword, long id);
}
