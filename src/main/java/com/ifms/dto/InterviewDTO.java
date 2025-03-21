package com.ifms.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class InterviewDTO {
    private Long candidateId;
    private Long interviewerId;
    private LocalDateTime scheduledTime;
}

