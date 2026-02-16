package com.example.touristWebsite.service.impl;

import com.example.touristWebsite.dto.AuthRequestDTO;
import com.example.touristWebsite.dto.AuthResponseDTO;
import com.example.touristWebsite.dto.UserDTO;

import com.example.touristWebsite.dto.UserResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    UserResponseDTO addUsers(UserDTO userDTO);


    List<UserResponseDTO> getAllUsers(Authentication authentication);

    UserResponseDTO getSingleUser(Authentication authentication);
}
