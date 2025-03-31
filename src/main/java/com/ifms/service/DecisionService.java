package com.ifms.service;

import com.ifms.entity.Decision;
import com.ifms.entity.FinalStatus;
import com.ifms.entity.Interview;
import com.ifms.entity.User;
import com.ifms.repository.DecisionRepository;
import com.ifms.repository.InterviewRepository;
import com.ifms.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DecisionService {
    private final DecisionRepository decisionRepository;
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;

    public DecisionService(DecisionRepository decisionRepository, InterviewRepository interviewRepository,UserRepository userRepository) {
        this.decisionRepository = decisionRepository;
        this.interviewRepository = interviewRepository;
        this.userRepository = userRepository;
    }

    /**
     * HR submits the final hiring decision.
     */
    public Decision submitDecision(Map<String, Object> requestData) {
        System.out.println("Received request data: " + requestData);  // Debugging log

        Long interviewId = Long.valueOf(requestData.get("interviewId").toString());
        Long hrId = Long.valueOf(requestData.get("hrId").toString());

        // Fetch Interview from DB
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        // Fetch HR from DB
        User hr_admin = userRepository.findById(hrId)
                .orElseThrow(() -> new RuntimeException("HR user not found"));

        // Create and set Decision object
        Decision decision = new Decision();
        decision.setInterview(interview);
        decision.setHr(hr_admin);
        
        try {
            decision.setFinalStatus(FinalStatus.valueOf(requestData.get("finalStatus").toString().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid FinalStatus value: " + requestData.get("finalStatus"));
        }

        decision.setComments(requestData.get("comments").toString());

        System.out.println("Saving Decision: " + decision);
        return decisionRepository.save(decision);
    }


    /**
     * Fetch all hiring decisions (For HR review).
     */
    public List<Decision> getAllDecisions() {
        return decisionRepository.findAll();
    }

    /**
     * Fetch decision for a specific interview.
     */
    public Decision getDecisionByInterview(Long interviewId) {
        Optional<Interview> interview = interviewRepository.findById(interviewId);
        return interview.map(decisionRepository::findByInterview)
                .orElseThrow(() -> new RuntimeException("Interview not found!"));
    }
}
