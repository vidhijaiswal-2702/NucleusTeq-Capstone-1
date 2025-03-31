package com.ifms.repository;

import com.ifms.entity.Decision;
import com.ifms.entity.Interview;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DecisionRepository extends JpaRepository<Decision, Long> {
    Optional<Decision> findByInterviewId(Long interviewId);  // Check if decision exists for an interview
    Decision findByInterview(Interview interview);
}
