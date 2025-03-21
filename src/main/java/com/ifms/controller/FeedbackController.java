package com.ifms.controller;

import com.ifms.dto.FeedbackDTO;
import com.ifms.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackDTO> createFeedback(@RequestBody FeedbackDTO dto) {
        FeedbackDTO savedFeedback = feedbackService.createFeedback(dto);
        return ResponseEntity.ok(savedFeedback);
    }

    // ✅ New Endpoint: Get Feedback by Interview ID
    @GetMapping("/interview/{interviewId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackByInterviewId(@PathVariable Long interviewId) {
        List<FeedbackDTO> feedbacks = feedbackService.getFeedbackByInterviewId(interviewId);
        return ResponseEntity.ok(feedbacks);
    }
}

