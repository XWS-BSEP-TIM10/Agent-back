package com.agent.exception;

public class APITokenNotFoundException extends Exception{
    public APITokenNotFoundException() {
        super("API token not found");
    }
}
