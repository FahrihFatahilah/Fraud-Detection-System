package com.rest.fds.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.fds.entity.Login;
import com.rest.fds.entity.LoginResponse;
import com.rest.fds.entity.RegisterUser;
import com.rest.fds.entity.User;
import com.rest.fds.service.AuthenticationService;
import com.rest.fds.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUser request) {
        logger.info(">>> incoming register user");
        User registeredUser = authenticationService.signup(request);

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String a = objectMapper.writeValueAsString(request);
            logger.info("debug {}", a);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        logger.info(">>> user register request {} ",request);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody Login request) {
        logger.info(">>> incoming login");

        User authenticatedUser = authenticationService.authenticate(request);

        String jwtToken = jwtService.generateToken((UserDetails) authenticatedUser);

        logger.info(">>> user login request {} / token :  {} ",authenticatedUser,jwtToken);


        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwtToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
//        loginResponse.setUsername();
        return ResponseEntity.ok(loginResponse);
    }
}