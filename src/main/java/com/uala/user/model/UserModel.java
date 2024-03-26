package com.uala.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserModel {
    private String id;
    private String username;
    private String email;
    private String password;
    private List<String> followers;
    private List<String> following;

}

