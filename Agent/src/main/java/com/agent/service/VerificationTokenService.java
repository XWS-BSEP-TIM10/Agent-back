package com.agent.service;

import com.agent.model.User;
import com.agent.model.VerificationToken;
import com.agent.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public VerificationToken generateVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user);
        return verificationTokenRepository.save(verificationToken);
    }

    public VerificationToken findVerificationTokenByToken(String token) {
        return verificationTokenRepository.findVerificationTokenByToken(token);
    }


    public VerificationToken findVerificationTokenByUser(String id) {
        return verificationTokenRepository.findVerificationTokenByUserId(id);
    }

    public void delete(VerificationToken verificationToken) {
        verificationTokenRepository.delete(verificationToken);
    }
}
