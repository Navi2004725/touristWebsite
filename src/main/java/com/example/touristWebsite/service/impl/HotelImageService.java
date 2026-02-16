package com.example.touristWebsite.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface HotelImageService {
    String uploadImage(int hotelId, MultipartFile file);
}
