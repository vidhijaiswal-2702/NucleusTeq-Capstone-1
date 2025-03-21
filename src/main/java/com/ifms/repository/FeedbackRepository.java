package com.ifms.repository;

import com.ifms.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    // Custom query method to find feedback by interview ID
    List<Feedback> findByInterviewId(Long interviewId);
}

