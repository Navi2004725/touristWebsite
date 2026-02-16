package com.example.touristWebsite.repo;
import com.example.touristWebsite.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    Optional<RoomEntity> findById(Long id);


    // Get rooms for a specific hotel filtered by room type (case-insensitive)
    List<RoomEntity> findByHotel_HotelIdAndRoomTypeContainingIgnoreCase(int hotelId, String roomType);



}

