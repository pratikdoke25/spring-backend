package com.pratik.jGame.exception;

public class MazeGenerationException extends RuntimeException{
    public MazeGenerationException(String message, Exception e) {
        super(message, e);
    }

    public MazeGenerationException(String message) {
        super(message);
    }
}
