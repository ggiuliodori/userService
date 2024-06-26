package com.uala.user.service;

import com.uala.user.model.entity.UserEntity;
import com.uala.user.model.api.UserModelResponse;
import com.uala.user.parser.UserParser;
import com.uala.user.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserParser userParser;

    @Autowired
    private CacheClearService cacheClearService;

    @Cacheable(value = "userCache", key = "#id")
    public UserModelResponse getUser(String id) {
        log.info("Fetching user with id {} from database", id);
        return userRepository.findById(id).map(userParser::userEntityToUserApiModel).orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));
    }

    @Transactional
    public void followUser(String userId, String followedUserId) {
        UserEntity user = userRepository.findById(String.valueOf(userId)).orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        UserEntity followedUser = userRepository.findById(String.valueOf(followedUserId)).orElseThrow(() -> new IllegalArgumentException("User with id " + followedUserId + " not found"));

        user.getFollowing().add(followedUser);
        userRepository.save(user);

        log.info("Clear cache for users {} - {}", userId, followedUserId);
        cacheClearService.evictUserCache(userId);
        cacheClearService.evictUserCache(followedUserId);

    }

    public Page<UserModelResponse> getAllUsers(Pageable pageable) {
        Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        return userEntityPage.map(userParser::userEntityToUserApiModel);
    }
}
