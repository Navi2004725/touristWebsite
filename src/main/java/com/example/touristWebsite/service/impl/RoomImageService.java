package com.example.touristWebsite.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface RoomImageService {
    String uploadImage(Long id, MultipartFile file);

    String deleteRoomImage(int imageId, Long roomId);
}
