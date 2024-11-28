package com.nlo.service;

import com.nlo.entity.User;
import com.nlo.mapper.UserMapper;
import com.nlo.model.AuthRequest;
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

import java.util.Comparator;
import java.util.List;
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
        if((Objects.isNull(user.getUsername()) || user.getUsername().isBlank()) && Objects.nonNull(user.getMobile())) {
            user.setUsername(userDto.getMobile());
        }
        String password = userDto.getPassword();

        if(password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

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

    public List<UserDto> getUserByConstituency(String constituencyId) {
        return userRepository.findByConstituencyId(constituencyId).stream().map(userMapper::toDto)
                .filter(e -> Objects.nonNull(e.getUsername()))
                .sorted(Comparator.comparing(UserDto::getUsername))
                .toList();
    }

    @Transactional
    public User findByMobile(AuthRequest authenticationRequest) {
        return userRepository.findByMobile(authenticationRequest.getPhoneNo())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public String getMobileByMembershipId(String memberShipId) {
        return userRepository.findByMemberShipId(memberShipId)
                .map(User::getMobile)
                .orElse(null);
    }
}
