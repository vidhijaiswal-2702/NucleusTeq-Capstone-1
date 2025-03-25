package com.ifms.service;

import com.ifms.dto.AssignInterviewDTO;
import com.ifms.dto.FeedbackReviewDTO;
import com.ifms.dto.InterviewDecisionDTO;
import com.ifms.dto.InterviewSummaryDTO;
import com.ifms.dto.ScheduleRoundDTO;

import java.util.List;

public interface HrDashboardService {
    List<InterviewSummaryDTO> getAssignedInterviews();
    FeedbackReviewDTO getFeedbackForReview(Long interviewId);
    String makeFinalDecision(InterviewDecisionDTO decisionDTO);
    void assignInterview(AssignInterviewDTO dto);
    void deleteInterview(Long interviewId);
    void scheduleFurtherRound(Long interviewId, ScheduleRoundDTO scheduleRoundDTO);
}
