package org.news.aggregator.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.news.aggregator.entity.LoginDto;
import org.news.aggregator.entity.LoginResponse;
import org.news.aggregator.entity.UserInfo;
import org.news.aggregator.entity.UserDto;
import org.news.aggregator.service.JWTService;
import org.news.aggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class RegistrationController {

    @Autowired
    UserService service;

    @Autowired
    private JWTService jwtService;

    @GetMapping("/hello")
    public String registerUser(){
        return "HelloWorld";
    }

    @PostMapping("/registerUser")
    public UserInfo registerUser(@RequestBody UserDto userDto, HttpServletRequest request){
        UserInfo userDetails = service.registerUser(userDto);
        String token = UUID.randomUUID().toString();
        String applicationUrl = getApplicationUrl(request) + "/verifyRegistration?token=" + token;
        service.createVerificationToken(userDetails, token);
        System.out.println("Verification token created for user: " + userDetails.getEmail());
        System.out.println("Verification url: " + applicationUrl);
        return userDetails;
    }

    @PostMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam String token) {
        boolean isValid = service.validateTokenAndEnableUser(token);
        if (!isValid) {
            return "Invalid token";
        }
        return "User enabled successfully";
    }


    private String getApplicationUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @GetMapping("/users")
    public List<UserInfo> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping("/loginUser")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginUserDto, HttpServletRequest request) {
        UserInfo authenticatedUser = service.authenticateUser(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}

