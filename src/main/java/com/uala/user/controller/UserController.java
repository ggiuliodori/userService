package com.uala.user.controller;

import com.uala.user.model.api.UserModelResponse;
import com.uala.user.model.entity.UserEntity;
import com.uala.user.repository.UserRepository;
import com.uala.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity newUser = userRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserModelResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserModelResponse> usersPage = userService.getAllUsers(pageable);
        if (usersPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(usersPage, HttpStatus.OK);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserModelResponse> getUser(@PathVariable String id) {
        Optional<UserModelResponse> user = Optional.ofNullable(userService.getUser(id));
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestParam String userId, @RequestParam String followedUserId) {
        userService.followUser(userId, followedUserId);
        return ResponseEntity.status(HttpStatus.OK).body("User with id " + userId + " is now following user with id " + followedUserId);
    }
}


