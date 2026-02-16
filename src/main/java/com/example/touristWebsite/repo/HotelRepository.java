package com.example.touristWebsite.repo;

import com.example.touristWebsite.entity.HotelEntity;
import com.example.touristWebsite.entity.HotelImageEntity;
import com.example.touristWebsite.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<HotelEntity, Integer> {
    Optional<HotelEntity> findById(int id);

    List<HotelEntity> findByHotelAddressContainingIgnoreCase(String hotelAddress);

}
    
    

