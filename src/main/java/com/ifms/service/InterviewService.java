package com.ifms.service;

import com.ifms.entity.Interview;
import com.ifms.entity.User;
import com.ifms.exception.ResourceNotFoundException;
import com.ifms.entity.InterviewStatus;
import com.ifms.repository.DecisionRepository;
import com.ifms.repository.FeedbackRepository;
import com.ifms.repository.InterviewRepository;
import com.ifms.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;
    private final DecisionRepository decisionRepository;
    private final FeedbackRepository feedbackRepository;

    public InterviewService(InterviewRepository interviewRepository, UserRepository userRepository,DecisionRepository decisionRepository, FeedbackRepository feedbackRepository ) {
        this.interviewRepository = interviewRepository;
        this.userRepository = userRepository;
        this.decisionRepository = decisionRepository;
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * HR schedules a new interview.
     */
    public Interview scheduleInterview(Interview interview) {
        return interviewRepository.save(interview);
    }

    /**
     * HR updates an existing interview.
     */
    public Interview updateInterview(Long interviewId, Interview updatedInterview) {
        Optional<Interview> existingInterview = interviewRepository.findById(interviewId);
        if (existingInterview.isPresent()) {
            Interview interview = existingInterview.get();
            interview.setCandidateName(updatedInterview.getCandidateName());
            interview.setCandidateEmail(updatedInterview.getCandidateEmail());
            interview.setInterviewer(updatedInterview.getInterviewer());
            interview.setInterviewDate(updatedInterview.getInterviewDate());
            interview.setRound(updatedInterview.getRound());
            interview.setStatus(updatedInterview.getStatus());
            return interviewRepository.save(interview);
        } else {
            throw new RuntimeException("Interview not found!");
        }
    }

    /**
     * HR deletes an interview.
     */
    @Transactional
    public void deleteInterview(Long id) {
        if (interviewRepository.existsById(id)) {
            // Delete all feedback related to this interview
            feedbackRepository.deleteByInterviewId(id);
            
            // Delete all decisions related to this interview
            decisionRepository.deleteByInterviewId(id);

            // Now delete the interview
            interviewRepository.deleteById(id);
        } else {
            throw new RuntimeException("Interview not found with ID: " + id);
        }
    }



    /**
     * HR fetches all scheduled interviews.
     */
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    /**
     * Interviewers fetch their assigned interviews.
     */
    public List<Interview> getInterviewsByInterviewer(User interviewer) {
        return interviewRepository.findByInterviewer(interviewer);
    }
    public Interview getInterviewById(Long id) {
        return interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found with ID: " + id));
    }
    
    public List<Interview> getAssignedInterviewsByEmail(String email) {
        // Fetch interviewer by email
        User interviewer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Interviewer not found"));

        // Fetch assigned interviews using interviewer ID
        return interviewRepository.findByInterviewerId(interviewer.getId());
    }

}
