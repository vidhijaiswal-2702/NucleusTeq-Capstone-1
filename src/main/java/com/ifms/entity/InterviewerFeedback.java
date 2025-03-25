package com.ifms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "interviewer_feedback")
public class InterviewerFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @OneToMany(mappedBy = "interviewerFeedback", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationMetric> evaluationMetrics;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DecisionLevel decisionLevel; // Final decision (L1 Passed, L2 Required, etc.)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvaluationRound evaluationRound; // L1 or L2 feedback round

    @Column(columnDefinition = "TEXT")
    private String finalComments; // Optional interviewer comments
}

