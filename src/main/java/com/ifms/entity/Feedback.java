package com.ifms.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "feedback")
@Getter
@Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;  // FK to Interviews table

    @ManyToOne
    @JoinColumn(name = "interviewer_id", nullable = false)
    private User interviewer;  // FK to Users table

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rating rating;

    @Column(columnDefinition = "TEXT")
    private String topicsUsed;  // Specific topics tested

    @Column(columnDefinition = "TEXT")
    private String comments;  // Interviewer's feedback comments
}
