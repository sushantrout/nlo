package com.nlo.mapper;

import com.nlo.entity.Constituency;
import com.nlo.entity.User;
import com.nlo.mapper.BaseMapper;
import com.nlo.model.ConstituencyDTO;
import com.nlo.model.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional
public class UserMapper implements BaseMapper<UserDto, User> {
    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMobile(user.getMobile());
        userDto.setName(user.getName());
        userDto.setProfileImage(user.getProfileImage());
        if(Objects.nonNull(user.getConstituency())) {
            ConstituencyDTO constituencyDTO = new ConstituencyDTO();
            constituencyDTO.setId(user.getConstituency().getId());
            constituencyDTO.setTitle(user.getConstituency().getTitle());
            userDto.setConstituencyDTO(constituencyDTO);
        }
        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMobile(userDto.getMobile());
        user.setName(userDto.getName());
        user.setProfileImage(userDto.getProfileImage());
        if(Objects.nonNull(user.getConstituency())) {
            Constituency constituency = new Constituency();
            constituency.setId(user.getConstituency().getId());
            constituency.setTitle(user.getConstituency().getTitle());
            user.setConstituency(constituency);
        }
        return user;
    }
}