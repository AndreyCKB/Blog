package com.example.rest.spring.blog.repositories;

import com.example.rest.spring.blog.models.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmailIgnoreCase(String email);

}
