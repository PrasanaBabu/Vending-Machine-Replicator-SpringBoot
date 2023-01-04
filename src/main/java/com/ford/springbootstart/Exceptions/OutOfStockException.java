package com.ford.springbootstart.Exceptions;

public class OutOfStockException extends Exception{
    public OutOfStockException(String message) {
        super(message);
    }
}
