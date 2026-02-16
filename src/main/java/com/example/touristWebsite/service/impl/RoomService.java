package com.example.touristWebsite.service.impl;

import com.example.touristWebsite.dto.RoomDTO;
import com.example.touristWebsite.dto.RoomResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoomService {
    RoomDTO createRoom(RoomDTO roomDTO);

    List<RoomResponseDTO> getAllHotelRooms();

    RoomResponseDTO getRoomDetails(Long id);

    RoomResponseDTO updateRoomDetails(RoomDTO roomDTO, Long id);

    RoomResponseDTO deleteExistedRoom(Long id);

    List<RoomResponseDTO> getRoomByHotel(int hotelId);

    List<RoomResponseDTO> searchRoomsByType(int hotelId, String type);
}
