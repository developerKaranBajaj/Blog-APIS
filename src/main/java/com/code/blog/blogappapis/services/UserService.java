package com.code.blog.blogappapis.services;
import com.code.blog.blogappapis.entities.User;
import com.code.blog.blogappapis.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer UserId);
    List<UserDto> getAllUser();
    void deleteUser(Integer userId);
}
