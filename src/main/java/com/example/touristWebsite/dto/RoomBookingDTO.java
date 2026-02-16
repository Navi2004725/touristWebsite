package com.example.touristWebsite.dto;

import com.example.touristWebsite.entity.HotelEntity;
import com.example.touristWebsite.entity.RoomEntity;
import com.example.touristWebsite.entity.RoomPaymentsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomBookingDTO {
    private int bookingId;
    private String name;
    private String phoneNumber;
    private String email;
    private Date booked_date;
    private Long roomId;   // just the ID
    private Integer hotelId; // just the ID
    private List<RoomPaymentsEntity> payment;
}
