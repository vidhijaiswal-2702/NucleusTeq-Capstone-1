package com.ifms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "evaluation_metric")
public class EvaluationMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feedback_id", nullable = false)
    private InterviewerFeedback interviewerFeedback;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillCategory skillCategory; // Algorithm, SQL, Communication, etc.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rating rating; // Good, Poor, Average, etc.

    @Column(columnDefinition = "TEXT")
    private String comments; // Interviewer's evaluation comments
    
    @ManyToOne
    @JoinColumn(name = "candidate_evaluation_id")
    private CandidateEvaluation candidateEvaluation;

}

