package com.ford.springbootstart.Exceptions;

public class NoSuchProductException extends Exception{
    public NoSuchProductException(String message) {
        super(message);
    }
}
