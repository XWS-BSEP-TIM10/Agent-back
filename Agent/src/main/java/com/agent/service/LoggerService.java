package com.agent.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerService {

    private final Logger logger;

    public LoggerService(Class<?> parentClass) {
        this.logger = LogManager.getLogger(parentClass);
    }

    public void loginSuccess(String email) {
        logger.info("Login successful. Email: " + email);
    }

    public void loginFailed(String email) {
        logger.warn("Login failed. Email: " + email);
    }
    

    //-------------------------------------------------------------------------

}
