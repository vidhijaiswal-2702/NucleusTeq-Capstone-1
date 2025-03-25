package com.ifms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleRoundDTO {
    private LocalDateTime newInterviewDate;
    private String newInterviewerName;
}
