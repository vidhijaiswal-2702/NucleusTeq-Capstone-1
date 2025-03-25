package com.ifms.controller;

import com.ifms.entity.InterviewerFeedback;
import com.ifms.entity.EvaluationMetric;
import com.ifms.service.InterviewerDashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviewer-dashboard")
public class InterviewerDashboardController {

    @Autowired
    private InterviewerDashboardService dashboardService;

    @PostMapping("/submit-feedback")
    public InterviewerFeedback submitFeedback(@RequestBody InterviewerFeedback feedback) {
        return dashboardService.saveFeedback(feedback);
    }

    @GetMapping("/feedbacks")
    public List<InterviewerFeedback> getAllFeedbacks() {
        return dashboardService.getAllFeedbacks();
    }

    @GetMapping("/feedback/{id}/metrics")
    public List<EvaluationMetric> getEvaluationMetrics(@PathVariable Long id) {
        return dashboardService.getEvaluationMetricsByFeedbackId(id);
    }
}
