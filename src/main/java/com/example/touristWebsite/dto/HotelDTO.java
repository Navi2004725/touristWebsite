package com.example.touristWebsite.dto;

import com.example.touristWebsite.entity.HotelImageEntity;
import com.example.touristWebsite.entity.RoomBookingEntity;
import com.example.touristWebsite.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {
    private int hotelId;
    private String hotelName;
    private String hotelAddress;
    private List<HotelImageEntity> Hotel_images;
    private double rating;
    private String email;
    private String phone;
    private String description;
    private List<RoomEntity> rooms;
    private List<RoomBookingEntity> bookings;
}
