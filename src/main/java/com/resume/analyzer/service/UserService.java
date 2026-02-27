package com.resume.analyzer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.analyzer.model.User;
import com.resume.analyzer.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    // REGISTER USER
    public User registerUser(User user) {
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        return userRepo.save(user);
    }

    // LOGIN USER
    public User loginUser(String email, String password) {

        Optional<User> userOptional = userRepo.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }
}