package com.ifms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AssignInterviewDTO {
    private String candidateName;
    private String candidateEmail;
    private String interviewerName;
    private String position;
    private LocalDateTime interviewDate;
}
