package com.uala.user.service;

import com.uala.user.model.UserModel;
import com.uala.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void followUser(UserModel follower, String followeeId) {
        UserModel followee = userRepository.findById(followeeId).orElse(null);
        if (followee != null) {
            if (!follower.getFollowing().contains(followeeId)) {
                follower.getFollowing().add(followeeId);
                userRepository.save(follower);
            }
            String followerId = follower.getId();
            if (!followee.getFollowers().contains(follower.getId())) {
                followee.getFollowers().add(followerId);
                userRepository.save(followee);
            }
        }
    }
}
