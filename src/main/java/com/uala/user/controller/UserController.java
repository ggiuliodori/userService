package com.uala.user.controller;

import com.uala.user.model.UserModel;
import com.uala.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/api/users")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        UserModel newUser = userRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/api/users")
    public ResponseEntity<Page<UserModel>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserModel> usersPage = userRepository.findAll(pageable);
        if (usersPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(usersPage, HttpStatus.OK);
        }
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable String id) {
        Optional<UserModel> user = userRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String id, @RequestBody UserModel user) {
        Optional<UserModel> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            user.setId(id); // Ensure the user id is set to the requested id
            UserModel updatedUser = userRepository.save(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


