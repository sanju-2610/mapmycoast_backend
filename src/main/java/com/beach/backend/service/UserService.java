package com.beach.backend.service;

import com.beach.backend.model.User;
import com.beach.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public String signup(String username, String password) {
        if (repo.findByUsername(username).isPresent()) {
            return "USER_EXISTS";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // (Plain for project – explain hashing in viva)
        repo.save(user);

        return "SIGNUP_SUCCESS";
    }

    public String login(String username, String password) {
        return repo.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .map(u -> "LOGIN_SUCCESS")
                .orElse("INVALID_CREDENTIALS");
    }
}
