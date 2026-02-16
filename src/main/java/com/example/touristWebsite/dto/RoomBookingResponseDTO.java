package com.example.touristWebsite.dto;

import com.example.touristWebsite.entity.RoomPaymentsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomBookingResponseDTO {
    private int bookingId;
    private String name;
    private String phoneNumber;
    private String email;
    private Date booked_date;
    private Long roomId;
    private int hotelId;
    private List<RoomPaymentResponseDTO> payments;
}
