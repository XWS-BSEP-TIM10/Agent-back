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

    public void generatePasswordlessLoginFailed(String message, String email) {
        logger.info("Generated passwordless login token failed: {}. Email: {}", message, email);
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

    public void unauthorizedAccess(String method, String path, String ip) {
        logger.warn("Unauthorized access to {}: {}. From: {}", method, path, ip);
    }

    public void twoFAStatusChanged(boolean enable2FA, String email) {
        logger.info("Two-factor authentication status changed on: {}. Email: {}", enable2FA, email);
    }

    public void login2FAFailedCodeNotMatching(String email, String ip) {
        logger.warn("Two-factor login failed, invalid code. Username: {} From: {}", email, ip);
    }

    public void twoFAStatusCheck(String email, String ip) {
        logger.info("Two-factor authentication status check done. Username: {} From: {}", email, ip);
    }

    public void two2FACheckFailed(String email, String ip) {
        logger.warn("Checking two-factor status failed. Username: {} From: {}", email, ip);
    }
}
