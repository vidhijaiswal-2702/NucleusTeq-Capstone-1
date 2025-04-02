package com.ifms.controller;

import com.ifms.entity.Feedback;
import com.ifms.repository.*;
import com.ifms.entity.Interview;
import com.ifms.entity.Rating;
import com.ifms.entity.Skill;
import com.ifms.entity.User;
import com.ifms.service.FeedbackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
	@Autowired  // Make sure this is present
    private FeedbackRepository feedbackRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InterviewRepository interviewRepository;
	
	
    private final FeedbackService feedbackService;
    
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * API: Interviewer submits feedback.
     */
    @PostMapping("/submit")
    public ResponseEntity<?> submitFeedback(@RequestBody Map<String, Object> requestData) {
        try {
            // ✅ 1. Extract interview ID
            Long interviewId = Long.valueOf(requestData.get("interviewId").toString());

            // ✅ 2. Fetch Interview
            Interview interview = interviewRepository.findById(interviewId)
                    .orElseThrow(() -> new RuntimeException("Interview not found"));

            // ✅ 3. Extract feedback list
            List<Map<String, Object>> feedbackList = (List<Map<String, Object>>) requestData.get("feedback");

            for (Map<String, Object> entry : feedbackList) {
                // ✅ 4. Ensure interviewerId exists in JSON
                if (!entry.containsKey("interviewerId") || entry.get("interviewerId") == null) {
                    return ResponseEntity.badRequest().body("Error: interviewerId is missing in feedback entry!");
                }

                // ✅ 5. Fetch interviewer by ID
                Long interviewerId = Long.valueOf(entry.get("interviewerId").toString());
                User interviewer = userRepository.findById(interviewerId)
                        .orElseThrow(() -> new RuntimeException("Interviewer not found: " + interviewerId));

                // ✅ 6. Create Feedback Object
                Feedback feedback = new Feedback();
                feedback.setInterview(interview);
                feedback.setInterviewer(interviewer); // ✅ Associate interviewer
                feedback.setSkill(Skill.valueOf(entry.get("skill").toString())); // Enum conversion
                feedback.setRating(Rating.valueOf(entry.get("rating").toString())); // Enum conversion
                feedback.setTopicsUsed(entry.get("topicsUsed").toString());
                feedback.setComments(entry.get("comments").toString());

                // ✅ 7. Store final decision if provided
                if (entry.containsKey("finalDecision")) {
                    feedback.setFinalDecision(entry.get("finalDecision").toString());
                } else {
                    feedback.setFinalDecision("Pending"); // Default decision
                }

                feedbackRepository.save(feedback); // ✅ Save feedback
            }

            return ResponseEntity.ok("Feedback submitted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error submitting feedback: " + e.getMessage());
        }
    }


    /**
     * API: HR fetches all feedback for an interview.
     */
    @GetMapping("/interview/{interviewId}")
    public ResponseEntity<List<Feedback>> getFeedbackByInterview(@PathVariable Long interviewId) {
        List<Feedback> feedbackList = feedbackService.getFeedbackByInterview(interviewId);
        return ResponseEntity.ok(feedbackList);
    }

    /**
     * API: Interviewer fetches their submitted feedback.
     */
    @GetMapping("/interviewer/{interviewerId}")
    public ResponseEntity<List<Feedback>> getFeedbackByInterviewer(@PathVariable Long interviewerId) {
        User interviewer = new User();
        interviewer.setId(interviewerId);
        List<Feedback> feedbackList = feedbackService.getFeedbackByInterviewer(interviewer);
        return ResponseEntity.ok(feedbackList);
    }
    @GetMapping("/interviewer")
    public ResponseEntity<List<Feedback>> getFeedbackByInterviewerEmail(@RequestParam String email) {
        List<Feedback> feedbacks = feedbackRepository.findByInterviewer_Email(email);
        return ResponseEntity.ok(feedbacks);
    }
}
