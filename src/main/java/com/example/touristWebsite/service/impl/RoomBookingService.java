package com.example.touristWebsite.service.impl;

import com.example.touristWebsite.dto.RoomBookingDTO;
import com.example.touristWebsite.dto.RoomBookingResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoomBookingService {
    RoomBookingResponseDTO addBooking(RoomBookingDTO roomBookingDTO);

    List<RoomBookingResponseDTO> getAllBooks();

    RoomBookingResponseDTO getBookingDetails(int bookingId);

    RoomBookingResponseDTO updateCurrentBooking(RoomBookingDTO roomBookingDTO, int id);

    RoomBookingResponseDTO deleteCurrentBooking(int bookingId);
}
