package com.ifms.controller;

import com.ifms.dto.AssignInterviewDTO;
import com.ifms.dto.FeedbackReviewDTO;
import com.ifms.dto.InterviewDecisionDTO;
import com.ifms.dto.InterviewSummaryDTO;
import com.ifms.dto.ScheduleRoundDTO;
import com.ifms.service.HrDashboardService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/hr")
public class HrDashboardController {

    private final HrDashboardService hrDashboardService;

    public HrDashboardController(HrDashboardService hrDashboardService) {
        this.hrDashboardService = hrDashboardService;
    }
    
    @PostMapping("/assignInterview")
    public ResponseEntity<String> assignInterview(@RequestBody AssignInterviewDTO dto) {
        hrDashboardService.assignInterview(dto);
        return ResponseEntity.ok("Interview assigned successfully.");
    }

    // 📌 Get all assigned interviews
    @GetMapping("/interviews")
    public List<InterviewSummaryDTO> getAssignedInterviews() {
        return hrDashboardService.getAssignedInterviews();
    }
    

    // 📌 Get feedback for an interview before making a decision
    @GetMapping("/feedback/{interviewId}")
    public FeedbackReviewDTO getFeedbackForReview(@PathVariable Long interviewId) {
        return hrDashboardService.getFeedbackForReview(interviewId);
    }

    // 📌 Make a final hiring decision
    @PostMapping("/final-decision")
    public String makeFinalDecision(@RequestBody InterviewDecisionDTO decisionDTO) {
        return hrDashboardService.makeFinalDecision(decisionDTO);
    }
    
    @PostMapping("/schedule-round/{interviewId}")
    public ResponseEntity<String> scheduleFurtherRound(
            @PathVariable Long interviewId,
            @RequestBody ScheduleRoundDTO scheduleRoundDTO) {
        
        hrDashboardService.scheduleFurtherRound(interviewId, scheduleRoundDTO);
        return ResponseEntity.ok("Interview round scheduled successfully!");
    }
    
    @DeleteMapping("/delete-interview/{interviewId}")
    public ResponseEntity<String> deleteInterview(@PathVariable Long interviewId) {
        hrDashboardService.deleteInterview(interviewId);
        return ResponseEntity.ok("Interview deleted successfully!");
    }
}
