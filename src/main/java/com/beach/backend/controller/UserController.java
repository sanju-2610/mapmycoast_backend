package com.beach.backend.controller;

import com.beach.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody Map<String, String> body) {
        String res = service.signup(body.get("username"), body.get("password"));
        return Map.of("status", res);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String res = service.login(body.get("username"), body.get("password"));
        return Map.of("status", res);
    }
}
