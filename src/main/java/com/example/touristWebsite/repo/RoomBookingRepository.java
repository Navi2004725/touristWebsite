package com.example.touristWebsite.repo;

import com.example.touristWebsite.entity.RoomBookingEntity;
import com.example.touristWebsite.entity.RoomImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomBookingRepository extends JpaRepository<RoomBookingEntity, Integer> {

    Optional<RoomBookingEntity> findById(int bookingId);
}
