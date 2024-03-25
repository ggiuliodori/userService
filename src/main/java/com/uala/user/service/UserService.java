package com.uala.user.service;

import com.uala.user.model.UserModel;
import com.uala.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void followUser(String followerId, String followeeId) {
        UserModel follower = userRepository.findById(followerId).orElse(null);
        UserModel followee = userRepository.findById(followeeId).orElse(null);
        if (follower != null && followee != null) {
            if (!follower.getFollowing().contains(followeeId)) {
                follower.getFollowing().add(followeeId);
                userRepository.save(follower);
            }
            if (!followee.getFollowers().contains(followerId)) {
                followee.getFollowers().add(followerId);
                userRepository.save(followee);
            }
        }
    }
}
