package com.ifms.service;

import com.ifms.entity.InterviewerFeedback;
import com.ifms.entity.EvaluationMetric;
import java.util.List;

public interface InterviewerDashboardService {

    InterviewerFeedback saveFeedback(InterviewerFeedback feedback);

    List<InterviewerFeedback> getAllFeedbacks();

    List<EvaluationMetric> getEvaluationMetricsByFeedbackId(Long feedbackId);
}
