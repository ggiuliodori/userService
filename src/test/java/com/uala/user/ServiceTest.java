package com.uala.user;

import com.uala.user.model.UserModel;
import com.uala.user.repository.UserRepository;
import com.uala.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserModel user;
    private UserModel followedUser;

    @BeforeEach
    void setUp() {
        user = new UserModel();
        user.setId("1");

        followedUser = new UserModel();
        followedUser.setId("2");
    }

    @Test
    void followUser_Successfully() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.findById("2")).thenReturn(Optional.of(followedUser));

        userService.followUser("1", "2");

        verify(userRepository, times(1)).save(user);
        assert(user.getFollowing().contains(followedUser));
    }

    @Test
    void followUser_UserNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.followUser("1", "2"));

        verify(userRepository, never()).save(any());
    }

    @Test
    void followUser_FollowedUserNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.followUser("1", "2"));

        verify(userRepository, never()).save(any());
    }
}
