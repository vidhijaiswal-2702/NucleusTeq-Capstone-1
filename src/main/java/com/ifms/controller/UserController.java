package com.ifms.controller;

import com.ifms.entity.User;
import com.ifms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * API: Fetch all registered interviewers (For HR use only)
     */
    @GetMapping("/interviewers")
    public ResponseEntity<List<User>> getAllInterviewers() {
        List<User> interviewers = userService.getAllInterviewers();
        return ResponseEntity.ok(interviewers);
    }
}
