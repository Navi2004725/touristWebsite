package com.example.touristWebsite.service.impl;

import com.example.touristWebsite.dto.GetHotelImageDTO;
import com.example.touristWebsite.dto.HotelImageResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public interface HotelImageService {
    String uploadImage(int hotelId, MultipartFile file);

    HotelImageResponseDTO deleteImage(int hotelId, int imageId);

    List<GetHotelImageDTO> getAllImages();


}
