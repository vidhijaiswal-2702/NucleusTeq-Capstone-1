package com.ifms.repository;

import com.ifms.entity.User;
import com.ifms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // For authentication
    List<User> findByRole(Role role);  // Fetch all interviewers when HR assigns interviews
    Optional<User> findById(User HR_ADMIN); // ✅ This method exists in JpaRepository
    User findByEmailAndPassword(String email, String password);
}