package com.ifms.repository;

import com.ifms.entity.Feedback;
import com.ifms.entity.Interview;
import com.ifms.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByInterviewId(Long interviewId);  // Fetch all feedback for a specific interview
    List<Feedback> findByInterview(Interview interview);
    List<Feedback> findByInterviewer(User interviewer);
    List<Feedback> findByInterviewer_Email(String email);

}
