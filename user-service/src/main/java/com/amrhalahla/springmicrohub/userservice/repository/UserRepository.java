package com.amrhalahla.springmicrohub.userservice.repository;

import com.amrhalahla.springmicrohub.userservice.enums.Active;
import com.amrhalahla.springmicrohub.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAllByActive(Active active);

}
