package com.ifms.dto;

import lombok.Data;

@Data
public class InterviewDecisionDTO {
    private Long interviewId;
    private String finalDecision;  // Example: "Hired", "Rejected", "On Hold"
    private String remarks;        // Additional comments from HR/Admin
}
