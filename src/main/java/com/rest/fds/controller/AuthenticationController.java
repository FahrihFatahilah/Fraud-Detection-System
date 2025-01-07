package com.rest.fds.controller;


import com.rest.fds.entity.Login;
import com.rest.fds.entity.LoginResponse;
import com.rest.fds.entity.RegisterUser;
import com.rest.fds.entity.User;
import com.rest.fds.service.AuthenticationService;
import com.rest.fds.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@Slf4j
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUser request) {

        log.info(">>> incoming register user");

        User registeredUser = authenticationService.signup(request);

        log.info(">>> user register request {} ", request);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody Login request) {

        log.info(">>> incoming login");

        User authenticatedUser = authenticationService.authenticate(request);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        log.info(">>> user login request {} / token :  {} ", authenticatedUser, jwtToken);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwtToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}