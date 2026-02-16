package com.example.touristWebsite.dto;

import com.example.touristWebsite.entity.HotelEntity;
import com.example.touristWebsite.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDTO {
    private Long roomId;
    private String roomNumber;
    private String roomType;
    private int capacity;
    private boolean available;
    private String description;
    private double pricePerNight;
    private int hotelId;
    private List<RoomImageResponseDTO> roomImages;  // Optional images
    private List<RoomBookingResponseDTO> bookings;
}

