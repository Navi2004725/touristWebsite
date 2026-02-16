package com.example.touristWebsite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomBookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    private String name;
    private String phoneNumber;
    private String email;
    private Date booked_date;

    // Relationship: A booking is for one room
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    // Optional: You can also keep a direct hotel link (denormalized for easy queries)
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;

    @OneToMany(mappedBy = "bookingId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomPaymentsEntity> roomPayments = new ArrayList<>();


}
