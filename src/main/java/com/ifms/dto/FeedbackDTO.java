package com.ifms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
    private Long id;
    private Long interviewId;
    private Long interviewerId;
    private String comments;
    private int rating;
}

