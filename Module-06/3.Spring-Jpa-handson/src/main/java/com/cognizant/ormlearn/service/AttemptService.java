package com.cognizant.ormlearn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ormlearn.model.Attempt;
import com.cognizant.ormlearn.repository.AttemptRepository;

@Service
public class AttemptService {

    @Autowired
    private AttemptRepository attemptRepository;

    /**
     * Hands On 3: get the full attempt detail (questions, options, and the
     * user's selected answers) for a given user and attempt id.
     */
    @Transactional
    public Attempt getAttempt(int userId, int attemptId) {
        return attemptRepository.getAttempt(userId, attemptId);
    }
}
