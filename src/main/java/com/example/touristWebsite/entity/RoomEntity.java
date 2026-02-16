package com.example.touristWebsite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String roomNumber;
    private String roomType; // Single, Double, Suite
    private int capacity;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomImageEntity> roomImages;
    private double pricePerNight;
    private boolean available;
    private String description;
    // Foreign key to HotelEntity
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomBookingEntity> bookings;

}
