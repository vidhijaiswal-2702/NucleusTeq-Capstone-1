package com.ifms.service;

import com.ifms.entity.User;
import com.ifms.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    // Assign role to a user
    public String assignRole(String email, String role) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            return "User not found";
        }

        User user = userOptional.get();
        user.setRole(role);
        userRepository.save(user);

        return "Role assigned successfully";
    }

    // Get user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public String deleteUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            return "User not found";
        }

        userRepository.delete(userOptional.get());
        return "User deleted successfully";
    }

}
