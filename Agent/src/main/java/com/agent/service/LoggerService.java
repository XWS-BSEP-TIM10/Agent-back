package com.agent.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerService {

    private final Logger logger;

    public LoggerService(Class<?> parentClass) {
        this.logger = LogManager.getLogger(parentClass);
    }

    public void loginSuccess(String email, String ip) {
        logger.info("Login successful. Email: {}. From: {}.", email, ip);
    }

    public void loginFailed(String email, String ip) {
        logger.warn("Login failed. Email: {}. From: {}.", email, ip);
    }

    public void addedAPIToken(String email) {
        logger.info("API Token added successfully. Email: {}", email);
    }

    public void accountConfirmed(String email) {
        logger.info("Account confirmed successfully. Email: {}", email);
    }

    public void accountConfirmedFailedTokenNotFound(String token) {
        logger.warn("Failed to confirm account, token {} not found", token);
    }

    public void accountConfirmedFailedTokenExpired(String token, String email) {
        logger.warn("Failed to confirm account, token {} expired. for user {}", token, email);
    }

    public void accountRecovered(String email) {
        logger.info("Account recovered successfully. Email: {}", email);
    }

    public void accountRecoverFailedUserNotFound(String email) {
        logger.warn("Account recover failed. Email {} not found", email);
    }

    public void passwordRecoveredSuccessfully(String email) {
        logger.info("Password recovered successfully. Email: {}", email);
    }

    public void generatePasswordlessLogin(String email) {
        logger.info("Generated passwordless login token successfully. Email: {}", email);
    }

    public void passwordlessLoginSuccess(String email, String ip) {
        logger.info("Passwordless login successful. Email: {}. From: {}", email, ip);
    }

    public void passwordlessLoginFailed(String ip) {
        logger.warn("Passwordless login failed. Attempt from: {}", ip);
    }

    public void createInterviewSuccess(String email) {
        logger.info("Interview successfully created. Email: {}", email);
    }

    public void createInterviewFailure(String email) {
        logger.warn("Failed to create interview for user: {}", email);
    }

    public void createCompanySuccess(String email) {
        logger.info("Company request successfully created. Email: {}", email);
    }

    public void createCompanyFailure(String email) {
        logger.warn("Company request creation failed. Email: {}", email);
    }

    public void updateCompanySuccess(String email) {
        logger.info("Company successfully updated. Email: {}", email);
    }

    public void updateCompanyFailure(String email) {
        logger.warn("Company update failed. Email: {}", email);
    }

    public void activateCompanySuccess(String email, String id) {
        logger.info("Company {} successfully activated by {}", id, email);
    }

    public void activateCompanyFailure(String email, String id) {
        logger.warn("Company {} unsuccessfully activated by {}", id, email);
    }

    public void removeCompanySuccess(String email, String id) {
        logger.info("Company {} successfully removed by {}", id, email);
    }

    public void removeCompanyFailure(String email, String id) {
        logger.warn("Company {} unsuccessfully removed by {}", id, email);
    }


    //-------------------------------------------------------------------------

}
