package com.uala.user;

import com.uala.user.model.UserModel;
import com.uala.user.repository.UserRepository;
import com.uala.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserModel userModel;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFollowUser_Successful() {
        UserModel follower = new UserModel("1", "follower", "follower@example.com", "password", new ArrayList<>(), new ArrayList<>());
        UserModel followee = new UserModel("2", "followee", "followee@example.com", "password", new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById("2")).thenReturn(Optional.of(followee));
        doReturn(userModel).when(userRepository).save(any(UserModel.class));

        userService.followUser(follower, "2");

        verify(userRepository, times(1)).findById("2");
        verify(userRepository, times(1)).save(follower);
        verify(userRepository, times(1)).save(followee);

        assertEquals(1, follower.getFollowing().size());
        assertEquals("2", follower.getFollowing().get(0));

        assertEquals(1, followee.getFollowers().size());
        assertEquals("1", followee.getFollowers().get(0));
    }

    @Test
    void testFollowUser_FolloweeNotFound() {
        UserModel follower = new UserModel("1", "follower", "follower@example.com", "password", new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById("2")).thenReturn(Optional.empty());

        userService.followUser(follower, "2");

        verify(userRepository, times(1)).findById("2");
        verify(userRepository, never()).save(any(UserModel.class));

        assertEquals(0, follower.getFollowing().size());
    }
}
