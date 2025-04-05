package com.amrhalahla.springmicrohub.userservice.dto;

import com.amrhalahla.springmicrohub.userservice.enums.Role;
import lombok.Data;

@Data
public class AuthUserDto {
    private String id;
    private String username;
    private String password;
    private Role role;
}