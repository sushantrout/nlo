package com.nlo.service;

import com.nlo.entity.User;
import com.nlo.mapper.UserMapper;
import com.nlo.model.UserDto;
import com.nlo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow();
        return userMapper.toDto(user);
    }

    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserDto updateUser(String id, UserDto userDto) {
        userRepository.findById(id).orElseThrow();
        User updated = userMapper.toEntity(userDto);
        updated.setId(id);
        User updatedUser = userRepository.save(updated);
        return userMapper.toDto(updatedUser);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    public UserDto getCurrentUser() {
        //From the securiry context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication.getPrincipal())) {
            return null;
        }
        try {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByUsername(principal.getUsername()).orElseThrow();
            return userMapper.toDto(user);
        } catch (Exception e) {
            return null;
        }
    }
}
