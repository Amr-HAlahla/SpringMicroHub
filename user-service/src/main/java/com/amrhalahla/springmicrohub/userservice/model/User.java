package com.amrhalahla.springmicrohub.userservice.model;

import com.amrhalahla.springmicrohub.userservice.enums.Active;
import com.amrhalahla.springmicrohub.userservice.enums.Role;
import lombok.*;

import javax.persistence.*;

@Entity(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Active active;

    @Embedded
    private UserDetails userDetails;
}
