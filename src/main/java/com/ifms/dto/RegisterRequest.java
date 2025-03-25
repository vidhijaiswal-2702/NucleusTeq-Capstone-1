package com.ifms.dto;

import com.ifms.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;  // Admin or Interviewer
}
