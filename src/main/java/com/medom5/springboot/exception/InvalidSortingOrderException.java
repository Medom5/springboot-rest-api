package com.medom5.springboot.exception;

public class InvalidSortingOrderException extends RuntimeException {
    public InvalidSortingOrderException(String message) {
        super(message);
    }

    public InvalidSortingOrderException(String message, Throwable cause) {}
}
