package com.code.blog.blogappapis.services.impl;

import com.code.blog.blogappapis.config.Constants;
import com.code.blog.blogappapis.entities.Role;
import com.code.blog.blogappapis.entities.User;
import com.code.blog.blogappapis.exceptions.ResourceNotFoundExceptions;
import com.code.blog.blogappapis.payloads.UserDto;
import com.code.blog.blogappapis.respositories.RoleRepo;
import com.code.blog.blogappapis.respositories.UserRepo;
import com.code.blog.blogappapis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        Role role =  this.roleRepo.findById(Constants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.DtoToUser(userDto);

        User savedUser = this.userRepo.save(user);
        return this.UserToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundExceptions("User", "id", userId));
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        User UpdatedUser = this.userRepo.save(user);

        return this.UserToDto(UpdatedUser);
    }

    @Override
    public UserDto getUserById(Integer UserId) {
        User user = this.userRepo.findById(UserId).orElseThrow(()->new ResourceNotFoundExceptions("User", "id", UserId));
        return this.UserToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {

        List<User> users = this.userRepo.findAll();

        List<UserDto> usersDto = users.stream().map(user->this.UserToDto(user)).collect(Collectors.toList());

        return usersDto;
    }

    @Override
    public void deleteUser(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundExceptions("user", "id", userId));
        this.userRepo.delete(user);

    }

    private User DtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);

        /*
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        */
        return user;

    }

    private UserDto UserToDto(User user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setAbout(user.getAbout());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
        return userDto;

    }
}
