package com.example.rest.spring.blog.exception;

public class ErrorMessageForUserException extends RuntimeException {
    public ErrorMessageForUserException(String s) {
        super(s);
    }
}
