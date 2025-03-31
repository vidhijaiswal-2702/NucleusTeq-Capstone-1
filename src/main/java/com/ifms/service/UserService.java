package com.ifms.service;

import com.ifms.entity.User;
import com.ifms.entity.Role;
import com.ifms.repository.UserRepository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Fetch all interviewers for HR to assign interviews.
     */
    public List<User> getAllInterviewers() {
        return userRepository.findByRole(Role.INTERVIEWER);
    }
    
    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
    
    public void registerUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)); // Hash password
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
    
    public User login(String email, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        
        if (optionalUser.isPresent()) {
            User user = optionalUser.get(); // Extract User from Optional
            if (BCrypt.checkpw(rawPassword, user.getPassword())) {
                return user; // ✅ Password matches
            }
        }
        return null; // ❌ Invalid credentials
    }

}
