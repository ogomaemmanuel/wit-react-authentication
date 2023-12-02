package com.wit.blogs;

import com.wit.blogs.dto.LoginRequestDto;
import com.wit.blogs.dto.LoginResponse;
import com.wit.blogs.dto.UserRegistrationDto;
import com.wit.blogs.entities.UserEntity;
import com.wit.blogs.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDto loginRequest) throws Exception {
        LoginResponse loginResponse=this.authService.authenticate(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@Valid @RequestBody UserRegistrationDto registrationDto) throws Exception {
        UserEntity loginResponse=this.authService.registerUser(registrationDto);
        return ResponseEntity.ok(loginResponse);
    }

}
