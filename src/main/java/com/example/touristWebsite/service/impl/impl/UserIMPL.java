package com.example.touristWebsite.service.impl.impl;

import com.example.touristWebsite.dto.RoomPaymentResponseDTO;
import com.example.touristWebsite.dto.UserDTO;
import com.example.touristWebsite.dto.UserResponseDTO;
import com.example.touristWebsite.entity.RoomPaymentsEntity;
import com.example.touristWebsite.entity.UserEntity;
import com.example.touristWebsite.repo.UserRepo;
import com.example.touristWebsite.service.impl.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
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

    public UserIMPL(
            UserRepo userRepo,
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
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

        // Encode password
        userEntity.setPassword(
                passwordEncoder.encode(userDTO.getPassword())
        );

        // Default role
        userEntity.setRole(
                userDTO.getRole() != null
                        ? userDTO.getRole()
                        : "user"
        );

        // Prevent null list
        userEntity.setRoomPayments(new ArrayList<>());

        // If payments exist
        if (userDTO.getPayments() != null) {

            userDTO.getPayments().forEach(payment -> {
                payment.setUserId(userEntity);
            });

            userEntity.setRoomPayments(userDTO.getPayments());
        }

        // Save user
        userRepo.save(userEntity);

        return getUserResponseDTO(userEntity);
    }

    @Override
    public List<UserResponseDTO> getAllUsers(Authentication authentication) {

        List<UserResponseDTO> userDTOs = new ArrayList<>();

        String email = authentication.getName();

        UserEntity currentUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Admin can see all users
        if ("admin".equals(currentUser.getRole())) {

            List<UserEntity> userEntities = userRepo.findAll();

            for (UserEntity userEntity : userEntities) {
                userDTOs.add(getUserResponseDTO(userEntity));
            }

        } else {

            // Normal user sees only himself
            userDTOs.add(getUserResponseDTO(currentUser));
        }

        return userDTOs;
    }

    @Override
    public UserResponseDTO getSingleUser(Authentication authentication) {

        String email = authentication.getName();

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return getUserResponseDTO(user);
    }

    // Convert RoomPaymentsEntity -> RoomPaymentResponseDTO
    public List<RoomPaymentResponseDTO> getRoomPayments(UserEntity userEntity) {

        List<RoomPaymentResponseDTO> roomPayments = new ArrayList<>();

        // Null safety check
        if (userEntity.getRoomPayments() != null) {

            for (RoomPaymentsEntity roomPaymentsEntity : userEntity.getRoomPayments()) {

                roomPayments.add(
                        modelMapper.map(
                                roomPaymentsEntity,
                                RoomPaymentResponseDTO.class
                        )
                );
            }
        }

        return roomPayments;
    }

    // Convert UserEntity -> UserResponseDTO
    public UserResponseDTO getUserResponseDTO(UserEntity userEntity) {

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(userEntity.getId());
        userResponseDTO.setEmail(userEntity.getEmail());
        userResponseDTO.setFirstName(userEntity.getFirstName());
        userResponseDTO.setLastName(userEntity.getLastName());
        userResponseDTO.setRole(userEntity.getRole());

        // Usually do NOT send password in response
        userResponseDTO.setPassword(userEntity.getPassword());

        userResponseDTO.setPayments(
                getRoomPayments(userEntity)
        );

        return userResponseDTO;
    }
}