package com.amrhalahla.springmicrohub.authservice.dto;

import com.amrhalahla.springmicrohub.authservice.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private Role role;
}
