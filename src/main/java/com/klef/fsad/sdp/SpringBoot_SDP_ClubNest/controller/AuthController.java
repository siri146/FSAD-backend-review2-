package com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.model.User;
import com.klef.fsad.sdp.SpringBoot_SDP_ClubNest.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        user.setRole("student");
        return userService.signup(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getEmail(), user.getPassword());
    }
}