package com.example.touristWebsite.dto;
import com.example.touristWebsite.entity.HotelEntity;
import com.example.touristWebsite.entity.RoomBookingEntity;
import com.example.touristWebsite.entity.RoomImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Long roomId;
    private String roomNumber;
    private String roomType; // Single, Double, Suite
    private int capacity;
    private List<RoomImageEntity> roomImages;
    private double pricePerNight;
    private boolean available;
    private String description;
    private HotelEntity hotel;
    private List<RoomBookingEntity> roomBooking;
}
