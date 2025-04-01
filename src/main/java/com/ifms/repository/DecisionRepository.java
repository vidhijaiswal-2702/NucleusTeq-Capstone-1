package com.ifms.repository;

import com.ifms.entity.Decision;
import com.ifms.entity.Interview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DecisionRepository extends JpaRepository<Decision, Long> {
    Optional<Decision> findByInterviewId(Long interviewId);  // Check if decision exists for an interview
    Decision findByInterview(Interview interview);
    
    @Modifying
    @Query("DELETE FROM Decision d WHERE d.interview.id = :interviewId")
    void deleteByInterviewId(@Param("interviewId") Long interviewId);

}
