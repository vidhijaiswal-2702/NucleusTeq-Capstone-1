package com.ifms.repository;

import com.ifms.model.Interview;
import com.ifms.model.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByStatus(InterviewStatus status);
}

