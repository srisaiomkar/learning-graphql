package com.example.graphql.exception;

public class QAndAAuthenticationException extends RuntimeException{
    public QAndAAuthenticationException(){
        super("Invalid credentials");
    }
}
