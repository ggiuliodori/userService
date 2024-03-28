package com.uala.user.model.api;

import com.uala.user.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModelResponse {

    private String id;
    private String username;
    private String password;
    private String email;
    private Set<String> following = new HashSet<>();
    private Set<String> follower = new HashSet<>();

}