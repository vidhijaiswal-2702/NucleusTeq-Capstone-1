package com.ifms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)  // Ensure enum is stored as a string in DB
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, unique = true)
    private String email; // Ensure this field is present and not null

}
