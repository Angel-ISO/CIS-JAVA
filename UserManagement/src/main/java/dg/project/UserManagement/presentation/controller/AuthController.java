package dg.project.UserManagement.presentation.controller;

import dg.project.UserManagement.infrastructure.service.UserService;
import dg.project.UserManagement.infrastructure.service.auth.AuthService;
import dg.project.UserManagement.presentation.dto.AuthCreateUserRequest;
import dg.project.UserManagement.presentation.dto.AuthLoginRequest;
import dg.project.UserManagement.presentation.dto.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody AuthCreateUserRequest authCreateUserRequest) {
        AuthResponse response = this.userService.create(authCreateUserRequest);

        if (response.status() == null || !response.status()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        AuthResponse response = authService.loginUser(userRequest);

        if (response.status() == null || !response.status()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
