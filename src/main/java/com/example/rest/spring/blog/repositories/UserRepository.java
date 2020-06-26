package com.example.rest.spring.blog.repositories;

import com.example.rest.spring.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long> {

    User findByEmailIgnoreCase(String email);

    @Modifying
    @Query("UPDATE User u SET u.email = ?1 WHERE u.id = ?2")
    void updateEmail(String newEmail, long id);

    @Modifying
    @Query("UPDATE User u SET u.password = ?1 WHERE u.id = ?2")
    int updatePassword(String newPassword, long id);


}
