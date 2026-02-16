package com.example.touristWebsite.dto;

import com.example.touristWebsite.entity.RoomPaymentsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private List<RoomPaymentResponseDTO> payments;
}
