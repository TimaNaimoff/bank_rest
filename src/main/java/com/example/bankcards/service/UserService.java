package com.example.bankcards.service;

import com.example.bankcards.dto.*;
import com.example.bankcards.entity.*;
import com.example.bankcards.exception.*;
import com.example.bankcards.mapper.*;
import com.example.bankcards.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
        return userMapper.toDto(user);
    }
}
