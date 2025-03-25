package com.ifms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InterviewSummaryDTO {
    private String candidateName;
    private String candidateEmail;
    private String interviewerName;
    private String position;
    private LocalDateTime interviewDate;
    private String status; // Changed from InterviewStatus enum to String for easy response
}
