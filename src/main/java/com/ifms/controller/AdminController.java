package com.ifms.controller;

import com.ifms.service.AdminService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/assign-role")
    public String assignRole(@RequestParam String username, @RequestParam String role) {
        return adminService.assignRole(username, role);
    }
    
    @PutMapping("/update-role")
    public ResponseEntity<String> updateUserRole(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newRole = request.get("role");

        String response = adminService.assignRole(email, newRole);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
        String response = adminService.deleteUser(email);
        return ResponseEntity.ok(response);
    }

    																																																																		
}

