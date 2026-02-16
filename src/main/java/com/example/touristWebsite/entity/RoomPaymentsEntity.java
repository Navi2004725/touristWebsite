package com.example.touristWebsite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomPaymentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;
    private double amount;
    @CreationTimestamp
    private LocalDateTime paymentDateTime;
    private String paymentPurpose;
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private RoomBookingEntity bookingId;

}
