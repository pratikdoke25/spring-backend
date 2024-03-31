package com.pratik.jGame.exception;

public class StatsCalculationException  extends RuntimeException{
    public StatsCalculationException(String message, Exception e) {
        super(message, e);
    }
}
