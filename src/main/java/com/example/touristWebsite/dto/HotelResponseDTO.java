package com.example.touristWebsite.dto;

import com.example.touristWebsite.entity.RoomBookingEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDTO {
    private int hotelId;
    private String hotelName;
    private String hotelAddress;
    private double rating;
    private String email;
    private String phone;
    private String description;

    private List<RoomResponseDTO> rooms;          // Rooms inside hotel
    private List<HotelImageResponseDTO> images;   // Hotel images
    private List<RoomBookingResponseDTO> bookings;
}
