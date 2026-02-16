package com.example.touristWebsite.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotelId;
    private String hotelName;
    private String hotelAddress;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<HotelImageEntity> Hotel_images;
    private double rating;
    private String email;
    private String phone;
    private String description;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<RoomEntity> rooms;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<RoomBookingEntity> bookings;


}
