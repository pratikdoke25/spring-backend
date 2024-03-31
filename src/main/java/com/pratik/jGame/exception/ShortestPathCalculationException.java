package com.pratik.jGame.exception;

public class ShortestPathCalculationException extends  RuntimeException{

    public ShortestPathCalculationException(String message, Exception e) {
        super(message, e);
    }

    public ShortestPathCalculationException(String message) {
        super(message);
    }
}
