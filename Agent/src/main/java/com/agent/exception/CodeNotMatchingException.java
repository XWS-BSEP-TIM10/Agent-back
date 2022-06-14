package com.agent.exception;

public class CodeNotMatchingException extends RuntimeException {
    public CodeNotMatchingException() {
        super("Code is not matching");
    }
    
}
