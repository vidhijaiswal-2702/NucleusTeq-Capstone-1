package com.ifms.repository;

import com.ifms.entity.DecisionLevel;
import com.ifms.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findByInterviewId(Long interviewId);
    List<Feedback> findByDecisionLevel(DecisionLevel decisionLevel);
}
