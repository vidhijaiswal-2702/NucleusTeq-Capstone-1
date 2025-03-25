package com.ifms.repository;

import com.ifms.entity.EvaluationMetric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationMetricRepository extends JpaRepository<EvaluationMetric, Long> {
}
