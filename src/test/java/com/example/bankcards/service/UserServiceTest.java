package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @InjectMocks private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = User.builder().id(1L).username("john").build();
    }

    @Test
    void getUserByUsername_success() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(new UserDto(1L, "john", null));

        UserDto dto = userService.getUserByUsername("john");

        assertThat(dto.getUsername()).isEqualTo("john");
    }

    @Test
    void getUserByUsername_notFound() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.getUserByUsername("john"));
    }
}
