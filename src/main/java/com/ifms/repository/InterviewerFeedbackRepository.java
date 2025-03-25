package com.ifms.repository;

import com.ifms.entity.InterviewerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewerFeedbackRepository extends JpaRepository<InterviewerFeedback, Long> {
}
