package com.example.touristWebsite.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomPaymentDTO {
    private int paymentId;
    private int userId;
    private double amount;
    private LocalDateTime paymentDateTime;
    private String paymentPurpose;
    private int bookingId;
}
