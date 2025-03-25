package com.ifms.repository;

import com.ifms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email); // Already exists for login
    
    Optional<User> findByUsername(String username);
    
}