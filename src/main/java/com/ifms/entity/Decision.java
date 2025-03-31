package com.ifms.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "decisions")
@Getter
@Setter
public class Decision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;  // FK to Interviews table

    @ManyToOne
    @JoinColumn(name = "hr_id", nullable = false)
    private User hr;  // FK to Users table (Only HR role)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FinalStatus finalStatus;  // Hired / Rejected / Next Round

    @Column(columnDefinition = "TEXT")
    private String comments;  // Justification for HR decision
}
