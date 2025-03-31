package com.ifms.repository;

import com.ifms.entity.Interview;
import com.ifms.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByInterviewerId(Long interviewerId);  // Fetch interviews assigned to a specific interviewer
    List<Interview> findByInterviewer(User interviewer);
}
