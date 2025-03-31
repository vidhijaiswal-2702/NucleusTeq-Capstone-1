package com.ifms.service;

import com.ifms.entity.Interview;
import com.ifms.entity.User;
import com.ifms.exception.ResourceNotFoundException;
import com.ifms.entity.InterviewStatus;
import com.ifms.repository.InterviewRepository;
import com.ifms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;

    public InterviewService(InterviewRepository interviewRepository, UserRepository userRepository) {
        this.interviewRepository = interviewRepository;
        this.userRepository = userRepository;
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
    public void deleteInterview(Long interviewId) {
        interviewRepository.deleteById(interviewId);
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
