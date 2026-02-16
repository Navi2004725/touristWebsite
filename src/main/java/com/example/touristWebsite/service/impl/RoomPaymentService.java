package com.example.touristWebsite.service.impl;

import com.example.touristWebsite.dto.RoomPaymentDTO;
import com.example.touristWebsite.dto.RoomPaymentResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoomPaymentService {
    RoomPaymentResponseDTO createNewPayment(RoomPaymentDTO roomPaymentDTO);

    List<RoomPaymentResponseDTO> getAllPayments();

    RoomPaymentResponseDTO getBookingPayment(int bookingId, int paymentId);

    RoomPaymentResponseDTO getRoomPayment(int paymentId);
}
