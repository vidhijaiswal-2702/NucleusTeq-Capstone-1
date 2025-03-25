package com.ifms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "interviews", uniqueConstraints = { @UniqueConstraint(columnNames = "candidateEmail") }) // Unique constraint at table level
public class Interview {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate ID
	    @Column(nullable = false, unique = true)
	    private Long id;

    @Column(nullable = false)
    private String candidateName;
    
    @Column(nullable = false, unique = true) // Ensures uniqueness and non-null
    private String candidateEmail;



    @Column(nullable = false)
    private String interviewerName;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private LocalDateTime interviewDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterviewStatus status;
    
    @Column(nullable = true)
    private String hrComments;  // HR's additional comments

    
    
}
