package com.uala.user;

import com.uala.user.controller.UserController;
import com.uala.user.model.UserModel;
import com.uala.user.repository.UserRepository;
import com.uala.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserModel user = new UserModel();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<UserModel> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetAllUsers() {
        List<UserModel> userList = new ArrayList<>();
        userList.add(new UserModel("1", "user1", "user1@example.com", "password", new HashSet<>()));
        userList.add(new UserModel("2", "user2", "user2@example.com", "password", new HashSet<>()));

        Page<UserModel> userPage = new PageImpl<>(userList);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        ResponseEntity<Page<UserModel>> response = userController.getAllUsers(0, 20);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userPage, response.getBody());
    }

    @Test
    void testGetUser() {
        String userId = "1";
        UserModel user = new UserModel(userId, "user1", "user1@example.com", "password", new HashSet<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<UserModel> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testFollowUser() {
        String userId = "1";
        String followId = "2";
        UserModel user = new UserModel(userId, "user1", "user1@example.com", "password", new HashSet<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userService).followUser(user.getId(), followId);

        ResponseEntity<String> response = userController.followUser(userId, followId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).followUser(user.getId(), followId);
    }
}

