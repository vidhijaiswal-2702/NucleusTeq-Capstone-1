package com.ifms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackReviewDTO {
    private String feedbackLevel;  // L1 or L2
    private String decisionLevel;  // Passed, Needs L2, Final Decision
    private String overallComments;
}
