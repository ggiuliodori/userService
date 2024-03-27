package com.uala.user.service;

import com.uala.user.model.UserModel;
import com.uala.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void followUser(String userId, String followedUserId) {
        UserModel user = userRepository.findById(String.valueOf(userId))
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        UserModel followedUser = userRepository.findById(String.valueOf(followedUserId))
                .orElseThrow(() -> new IllegalArgumentException("User with id " + followedUserId + " not found"));

        user.getFollowing().add(followedUser);
        userRepository.save(user);
    }
}
