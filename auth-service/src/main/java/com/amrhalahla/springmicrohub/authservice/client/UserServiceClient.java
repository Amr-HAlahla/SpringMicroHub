package com.amrhalahla.springmicrohub.authservice.client;

import com.amrhalahla.springmicrohub.authservice.dto.RegisterDto;
import com.amrhalahla.springmicrohub.authservice.dto.UserDto;
import com.amrhalahla.springmicrohub.authservice.request.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/v1/user")
public interface UserServiceClient {
    @PostMapping("/save")
    ResponseEntity<RegisterDto> save(@RequestBody RegisterRequest request);

    @GetMapping("/getUserByUsername/{username}")
    ResponseEntity<UserDto> getUserByUsername(@PathVariable String username);
}
