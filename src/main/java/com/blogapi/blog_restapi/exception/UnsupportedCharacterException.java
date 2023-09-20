package com.blogapi.blog_restapi.exception;

public class UnsupportedCharacterException extends RuntimeException{
    public UnsupportedCharacterException(String message) {
        super(message);
    }
}
