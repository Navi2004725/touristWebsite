package com.example.touristWebsite.repo;


import com.example.touristWebsite.entity.RoomPaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomPaymentRepository extends JpaRepository<RoomPaymentsEntity, Integer> {
    Optional<RoomPaymentsEntity> findById(int paymentId);
}
