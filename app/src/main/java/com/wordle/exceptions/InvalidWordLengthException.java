package com.wordle.exceptions;

public class InvalidWordLengthException extends Exception {
    public InvalidWordLengthException(String errorMessage) {
        super(errorMessage);
    }
}
