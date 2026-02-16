package com.example.touristWebsite.controller;

import com.example.touristWebsite.dto.AuthRequestDTO;
import com.example.touristWebsite.dto.AuthResponseDTO;
import com.example.touristWebsite.dto.UserDTO;
import com.example.touristWebsite.dto.UserResponseDTO;
import com.example.touristWebsite.service.impl.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController


public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> inputUsers(@RequestBody UserDTO userDTO) {
       UserResponseDTO users = userService.addUsers(userDTO);
       if(users != null) {
           return ResponseEntity.ok(users);

       }else {
           return ResponseEntity.badRequest().build();
       }


    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers(Authentication authentication) {
        List<UserResponseDTO> all_users = userService.getAllUsers(authentication);
        if(all_users != null) {
            return ResponseEntity.ok(all_users);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getIndividualUser(Authentication authentication) {
        UserResponseDTO user =  userService.getSingleUser(authentication);
        if(user != null) {
            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

}
