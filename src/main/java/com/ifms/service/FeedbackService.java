package com.ifms.service;

import com.ifms.dto.FeedbackDTO;
import com.ifms.model.Feedback;
import com.ifms.model.Interview;
import com.ifms.model.User;
import com.ifms.repository.FeedbackRepository;
import com.ifms.repository.InterviewRepository;
import com.ifms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Save Feedback
    public FeedbackDTO createFeedback(FeedbackDTO dto) {
        Interview interview = interviewRepository.findById(dto.getInterviewId())
                .orElseThrow(() -> new RuntimeException("Interview not found"));
        User interviewer = userRepository.findById(dto.getInterviewerId())
                .orElseThrow(() -> new RuntimeException("Interviewer not found"));

        Feedback feedback = new Feedback();
        feedback.setInterview(interview);
        feedback.setInterviewer(interviewer);
        feedback.setComments(dto.getComments());
        feedback.setRating(dto.getRating());

        Feedback savedFeedback = feedbackRepository.save(feedback);
        return mapToDTO(savedFeedback);
    }

    // ✅ Fetch Feedback for a Specific Interview
    public List<FeedbackDTO> getFeedbackByInterviewId(Long interviewId) {
        List<Feedback> feedbacks = feedbackRepository.findByInterviewId(interviewId);
        return feedbacks.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private FeedbackDTO mapToDTO(Feedback feedback) {
        return new FeedbackDTO(
                feedback.getId(),
                feedback.getInterview().getId(),
                feedback.getInterviewer().getId(),
                feedback.getComments(),
                feedback.getRating()
        );
    }
}
