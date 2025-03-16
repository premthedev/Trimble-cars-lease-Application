package com.trimble.carlease.dto;

import com.trimble.carlease.model.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private UserRole role;
}