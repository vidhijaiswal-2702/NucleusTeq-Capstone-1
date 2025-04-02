package com.ifms.service;

import com.ifms.entity.Feedback;
import com.ifms.entity.Interview;
import com.ifms.entity.Rating;
import com.ifms.entity.Skill;
import com.ifms.entity.User;
import com.ifms.repository.FeedbackRepository;
import com.ifms.repository.InterviewRepository;
import com.ifms.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, InterviewRepository interviewRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.interviewRepository = interviewRepository;
        this.userRepository = userRepository;
    }

    /**
     * Interviewer submits feedback for an interview.
     */
    public List<Feedback> submitFeedback(int interviewId, List<Map<String, Object>> feedbackList) {
        System.out.println("Processing feedback for interview ID: " + interviewId);
        System.out.println("Received feedbackList: " + feedbackList);  // 🔹 Print received data

        // ✅ Fetch Interview from DB
        Interview interview = interviewRepository.findById((long) interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found for ID: " + interviewId));

        List<Feedback> savedFeedbacks = new ArrayList<>();

        for (Map<String, Object> feedbackEntry : feedbackList) {
            try {
                System.out.println("Processing feedback entry: " + feedbackEntry);

                // ✅ Validate required fields
                String[] requiredFields = { "interviewerId", "skill", "rating", "topicsUsed", "comments" };

                for (String field : requiredFields) {
                    if (!feedbackEntry.containsKey(field) || feedbackEntry.get(field) == null) {
                        System.out.println("Missing field: " + field + " in feedback: " + feedbackEntry);
                        throw new IllegalArgumentException("Missing required field: " + field);
                    }
                }

                // ✅ Extract and validate interviewer ID
                Long interviewerId = Long.parseLong(feedbackEntry.get("interviewerId").toString());
                
                // ✅ Fetch Interviewer from DB using ID
                User interviewer = userRepository.findById(interviewerId)
                        .orElseThrow(() -> new RuntimeException("Interviewer not found for ID: " + interviewerId));

                // ✅ Create Feedback object
                Feedback feedback = new Feedback();
                feedback.setInterview(interview);
                feedback.setInterviewer(interviewer);
                feedback.setSkill(Skill.valueOf(feedbackEntry.get("skill").toString().toUpperCase()));
                feedback.setRating(Rating.valueOf(feedbackEntry.get("rating").toString().toUpperCase()));
                feedback.setTopicsUsed(feedbackEntry.get("topicsUsed").toString());
                feedback.setComments(feedbackEntry.get("comments").toString());

                // ✅ Store final decision if provided
                if (feedbackEntry.containsKey("finalDecision")) {
                    feedback.setFinalDecision(feedbackEntry.get("finalDecision").toString());
                } else {
                    feedback.setFinalDecision("Pending"); // Default decision
                }

                // ✅ Save feedback
                savedFeedbacks.add(feedbackRepository.save(feedback));

                System.out.println("Saved feedback: " + feedback);

            } catch (Exception e) {
                System.out.println("Error processing feedback entry: " + feedbackEntry);
                e.printStackTrace();  // 🔹 Print full error stack trace
                throw new RuntimeException("Invalid feedback data format", e);
            }
        }

        return savedFeedbacks;
    }



    /**
     * HR fetches all feedback for a specific interview.
     */
    public List<Feedback> getFeedbackByInterview(Long interviewId) {
        Optional<Interview> interview = interviewRepository.findById(interviewId);
        if (interview.isPresent()) {
            return feedbackRepository.findByInterview(interview.get());
        } else {
            throw new RuntimeException("Interview not found!");
        }
    }

    /**
     * Interviewer fetches their submitted feedback.
     */
    public List<Feedback> getFeedbackByInterviewer(User interviewer) {
        return feedbackRepository.findByInterviewer(interviewer);
    }
}
