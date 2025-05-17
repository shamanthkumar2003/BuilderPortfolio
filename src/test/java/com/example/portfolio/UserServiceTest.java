package com.example.portfolio;

import com.example.portfolio.dto.UserDTO;
import com.example.portfolio.model.User;
import com.example.portfolio.repository.UserRepository;
import com.example.portfolio.service.UserService;
import com.example.portfolio.service.impl.UserServiceImpl; // Import your impl
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;  // Use the implementation class here

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO createdUserDTO = userService.createUser(userDTO);

        assertNotNull(createdUserDTO);
        assertEquals(userDTO.getName(), createdUserDTO.getName());
        assertEquals(userDTO.getEmail(), createdUserDTO.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
    }
}
