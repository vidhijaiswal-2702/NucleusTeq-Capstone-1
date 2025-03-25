package com.ifms.repository;

import com.ifms.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    // ✅ Fetch interviews by candidate email
    List<Interview> findByCandidateName(String candidateName);

    // ✅ Fetch interviews by status
    List<Interview> findByStatus(String status);
    
    Optional<Interview> findByCandidateEmail(String email);

    
 // ✅ Fetch assigned interviews for a specific interviewer
    List<Interview> findByInterviewerName(String interviewerName);
    
    
}
