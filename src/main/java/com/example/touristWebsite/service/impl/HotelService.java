package com.example.touristWebsite.service.impl;

import com.example.touristWebsite.dto.HotelResponseDTO;
import com.example.touristWebsite.dto.HotelDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HotelService {
    HotelResponseDTO addHotel(HotelDTO hotelDTO);

    List<HotelResponseDTO> getAllHotels();

    HotelResponseDTO getHotelDetails(int id);

    HotelDTO updateHotelInfo(HotelDTO hotelDTO, int id);

    HotelDTO hotelDelete(int id);

    List<HotelResponseDTO> searchHotelByAddress(String keyword);
}
