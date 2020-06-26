package com.example.rest.spring.blog.service;

import com.example.rest.spring.blog.exception.ErrorMessageForUserException;

import java.util.Optional;

public interface SimpleService<T, ID> {

    <S extends T> S save(S var);

    Optional<T> findById(ID var);

    Iterable<T> findAll();

    void deleteById(ID var);
}
