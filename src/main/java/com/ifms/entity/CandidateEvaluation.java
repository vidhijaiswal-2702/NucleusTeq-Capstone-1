package com.ifms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidate_evaluations")
public class CandidateEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidateName;
    private String email;
    private String role;
    private String interviewDate;

    @Enumerated(EnumType.STRING)
    private EvaluationRound round;  // L1 or L2

    @Enumerated(EnumType.STRING)
    private DecisionLevel decisionLevel;  // L1_PASSED, L2_REQUIRED, etc.

    @OneToMany(mappedBy = "candidateEvaluation", cascade = CascadeType.ALL)
    private List<EvaluationMetric> evaluationMetrics;

    private String comments;  // Custom comments by the interviewer
}
