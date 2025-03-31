package com.ifms.service;

import com.ifms.entity.Role;
import com.ifms.entity.User;
import com.ifms.repository.UserRepository;
import com.ifms.util.PasswordUtil; // Import PasswordUtil for hashing

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    public User register(String username, String password, Role role, String email) {
        User user = new User();
        user.setUsername(username);

        // ✅ Hash password before storing
        String hashedPassword = PasswordUtil.hashPassword(password);
        user.setPassword(hashedPassword);
        
        user.setRole(role);
        user.setEmail(email); // Include email
        
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        // ✅ Verify password using hashed password check
        if (user.isPresent() && PasswordUtil.checkPassword(password, user.get().getPassword())) {
            return user.get();
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Fetch all users from DB
    }
}

