package com.example.touristWebsite.service.impl.impl;

import com.example.touristWebsite.dto.*;
import com.example.touristWebsite.entity.RoomPaymentsEntity;
import com.example.touristWebsite.entity.UserEntity;
import com.example.touristWebsite.service.impl.UserService;
import com.example.touristWebsite.repo.UserRepo;
import com.example.touristWebsite.util.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserIMPL implements UserService {
    private UserRepo userRepo;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;



    public UserIMPL(UserRepo userRepo, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;


    }

    @Override
    public UserResponseDTO addUsers(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setRole(userDTO.getRole() != null ? userDTO.getRole() : "user");
        if(userDTO.getPayments() != null) {
            userDTO.getPayments().forEach(payment -> payment.setUserId(userEntity));
            userEntity.setRoomPayments(userDTO.getPayments());
        }

        userRepo.save(userEntity);
        return getUserResponseDTO(userEntity);

    }

    @Override
    public List<UserResponseDTO> getAllUsers(Authentication authentication) {
        List<UserResponseDTO> userDTOs = new ArrayList<>();
        String email = authentication.getName();
        UserEntity currentUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if ("admin".equals(currentUser.getRole())) {
            // Admin → return all users
            List<UserEntity> userEntities = userRepo.findAll();
            for (UserEntity userEntity : userEntities) {
                userDTOs.add(getUserResponseDTO(userEntity));
            }
        } else {
            // Normal user → return only himself
            userDTOs.add(getUserResponseDTO(currentUser));
        }

        return userDTOs;

    }

    @Override
    public UserResponseDTO getSingleUser(Authentication authentication) {
        String email = authentication.getName();
        UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        return getUserResponseDTO(user);

    }

    public List<RoomPaymentResponseDTO> getRoomPayments(UserEntity userEntity) {
        List<RoomPaymentResponseDTO> roomPayments = new ArrayList<>();
        for (RoomPaymentsEntity roomPaymentsEntity : userEntity.getRoomPayments()) {
            roomPayments.add(modelMapper.map(roomPaymentsEntity, RoomPaymentResponseDTO.class));
        }
        return roomPayments;

    }

    public UserResponseDTO getUserResponseDTO(UserEntity userEntity) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userEntity.getId());
        userResponseDTO.setEmail(userEntity.getEmail());
        userResponseDTO.setFirstName(userEntity.getFirstName());
        userResponseDTO.setLastName(userEntity.getLastName());
        userResponseDTO.setRole(userEntity.getRole());
        userResponseDTO.setPassword(userEntity.getPassword());
        userResponseDTO.setPayments(getRoomPayments(userEntity));
        return userResponseDTO;
    }
}
