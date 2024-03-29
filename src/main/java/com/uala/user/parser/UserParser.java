package com.uala.user.parser;

import com.uala.user.model.api.UserModelResponse;
import com.uala.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserParser {

    public UserModelResponse userEntityToUserApiModel(UserEntity userEntity) {
        return Optional.ofNullable(UserModelResponse.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .following(userEntity.getFollowing().stream().map(UserEntity::getId).collect(Collectors.toSet()))
                .build()).orElse(null);
    }
}
