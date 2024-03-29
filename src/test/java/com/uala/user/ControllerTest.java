package com.uala.user;

import com.uala.user.controller.UserController;
import com.uala.user.model.api.UserModelResponse;
import com.uala.user.model.entity.UserEntity;
import com.uala.user.parser.UserParser;
import com.uala.user.repository.UserRepository;
import com.uala.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserParser userParser;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<UserEntity> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetAllUsers_ReturnsUsersPage() {
        // Mock data
        List<UserModelResponse> usersList = new ArrayList<>();
        usersList.add(new UserModelResponse("1", "user1", "user1@example.com", "password", new HashSet<>()));
        usersList.add(new UserModelResponse("2", "user2", "user2@example.com", "password", new HashSet<>()));
        Page<UserModelResponse> usersPage = new PageImpl<>(usersList);

        // Mocking userService
        Pageable pageable = PageRequest.of(0, 20);
        when(userService.getAllUsers(pageable)).thenReturn(usersPage);

        // Calling the controller method
        ResponseEntity<Page<UserModelResponse>> responseEntity = userController.getAllUsers(0, 20);

        // Asserting the response
        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertEquals(usersPage, responseEntity.getBody())
        );

        // Verifying that userService.getAllUsers was called with the correct arguments
        verify(userService).getAllUsers(pageable);
    }

    @Test
    void testGetUser() {
        String userId = "1";
        UserEntity user = new UserEntity(userId, "user1", "user1@example.com", "password", new HashSet<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<UserModelResponse> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userParser.userEntityToUserApiModel(user), response.getBody());
    }

    @Test
    void testFollowUser() {
        String userId = "1";
        String followId = "2";
        UserEntity user = new UserEntity(userId, "user1", "user1@example.com", "password", new HashSet<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userService).followUser(user.getId(), followId);

        ResponseEntity<String> response = userController.followUser(userId, followId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).followUser(user.getId(), followId);
    }
}

