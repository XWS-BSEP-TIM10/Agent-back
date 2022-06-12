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

    public void jobAdCreated(String email) {
        logger.info("Job ad successfully created. Email: {}", email);
    }

    public void jobAdCreatingFailed(String message, String email) {
        logger.warn("Job ad creation failed: {}. Email: {}", message, email);
    }

    public void jobAdsGettingFailed(String message) {
        logger.warn("Getting job ads failed: {}", message);
    }

    public void jobAdDeleted(String id, String email) {
        logger.info("Job ad: {} successfully deleted. Email: {}", id, email);
    }

    public void jobAdDeletingFailed(String id, String email) {
        logger.warn("Job ad: {} deletion failed. Email: {}", id, email);
    }

    public void jobAdShared(String id, String email) {
        logger.info("Job ad: {} successfully shared. Email {}", id, email);
    }

    public void jobAdSharingFailed(String id, String message, String email) {
        logger.warn("Job ad: {} sharing failed: {}. Email: {}", id, message, email);
    }

    public void reviewCreated(String email) {
        logger.info("Review successfully created. Email: {}", email);
    }

    public void reviewCreatingFailed(String message, String email) {
        logger.warn("Review creating failed: {}. Email: {}", message, email);
    }

    public void reviewsGettingFailed(String message) {
        logger.warn("Getting reviews failed: {}", message);
    }

    public void salaryCreated(String email) {
        logger.info("Salary successfully created. Email: {}", email);
    }

    public void salaryCreatingFailed(String message, String email) {
        logger.warn("Salary creating failed: {}. Email: {}", message, email);
    }

    public void salariesGettingFailed(String message) {
        logger.warn("Getting salaries failed: {}", message);
    }

    public void userSignedUp(String email) {
        logger.info("New user successfully signed up. Email: {}", email);
    }

    public void userSigningUpFailed(String message, String email) {
        logger.warn("New user signing up failed: {}. Email: {}", message, email);
    }

    public void passwordChanged(String email) {
        logger.info("Password successfully changed. Email: {}", email);
    }

    public void passwordChangingFailed(String message, String email) {
        logger.warn("Password changing failed: {}. Email: {}", message, email);
    }
}
