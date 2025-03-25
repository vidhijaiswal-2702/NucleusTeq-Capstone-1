package com.ifms.service;

import com.ifms.entity.InterviewerFeedback;
import com.ifms.entity.EvaluationMetric;
import com.ifms.repository.InterviewerFeedbackRepository;
import com.ifms.repository.EvaluationMetricRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewerDashboardServiceImpl implements InterviewerDashboardService {

    @Autowired
    private InterviewerFeedbackRepository feedbackRepository;

    @Autowired
    private EvaluationMetricRepository metricRepository;

    @Override
    public InterviewerFeedback saveFeedback(InterviewerFeedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<InterviewerFeedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<EvaluationMetric> getEvaluationMetricsByFeedbackId(Long feedbackId) {
        return metricRepository.findAll(); // Add filtering if needed
    }
}
