package com.ifms.entity;

import com.ifms.entity.DecisionLevel;
import com.ifms.entity.EvaluationRound;
import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_level", nullable = false)
    private EvaluationRound feedbackLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision_level", nullable = false)
    private DecisionLevel decisionLevel;

    @Column(name = "overall_comments", columnDefinition = "TEXT")
    private String overallComments;

    public Feedback() {}

    public Feedback(Interview interview, EvaluationRound feedbackLevel, DecisionLevel decisionLevel, String overallComments) {
        this.interview = interview;
        this.feedbackLevel = feedbackLevel;
        this.decisionLevel = decisionLevel;
        this.overallComments = overallComments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public EvaluationRound getFeedbackLevel() {
        return feedbackLevel;
    }

    public void setFeedbackLevel(EvaluationRound feedbackLevel) {
        this.feedbackLevel = feedbackLevel;
    }

    public DecisionLevel getDecisionLevel() {
        return decisionLevel;
    }

    public void setDecisionLevel(DecisionLevel decisionLevel) {
        this.decisionLevel = decisionLevel;
    }

    public String getOverallComments() {
        return overallComments;
    }

    public void setOverallComments(String overallComments) {
        this.overallComments = overallComments;
    }
}
