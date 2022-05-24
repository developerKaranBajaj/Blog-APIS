package com.code.blog.blogappapis.controllers;

import com.code.blog.blogappapis.entities.User;
import com.code.blog.blogappapis.payloads.ApiResponse;
import com.code.blog.blogappapis.payloads.UserDto;
import com.code.blog.blogappapis.services.UserService;
import com.code.blog.blogappapis.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Post -- create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }


    //PUT -- update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){

        UserDto  updatedUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);

    }

    //DELETE -- delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uid){
        this.userService.deleteUser(uid);
//        return ResponseEntity.ok(Map.of("message", "User Deleted successfully"));
//        return new ResponseEntity(Map.of("message", "User Deleted successfully"), HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);

    }

    //Get -- user get

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser(){
//        List<UserDto> allUser = this.userService.getAllUser();
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    //GET - single user get
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer uid){
        return ResponseEntity.ok(this.userService.getUserById(uid));
    }

}
