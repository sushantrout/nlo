package com.nlo.controller;

import com.nlo.model.UserDto;
import com.nlo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @PostMapping("/register")
    public UserDto saveUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping(value = "/{id}")
    public UserDto getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PutMapping(value = "/{id}")
    public UserDto updateUser(@PathVariable String id, UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }


    @GetMapping(value = "/me")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping(value = "/constituency/{constituencyId}")
    public List<UserDto> getUserByConstituency(@PathVariable String constituencyId) {
        return userService.getUserByConstituency(constituencyId);
    }

    @PutMapping
    public void updateUserProfile(@RequestParam MultipartFile profileImage) {
        userService.updateUserProfile(profileImage);
    }
}