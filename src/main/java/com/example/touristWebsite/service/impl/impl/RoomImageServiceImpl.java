package com.example.touristWebsite.service.impl.impl;

import com.example.touristWebsite.entity.HotelEntity;
import com.example.touristWebsite.entity.RoomEntity;
import com.example.touristWebsite.entity.RoomImageEntity;
import com.example.touristWebsite.repo.RoomImageRepository;
import com.example.touristWebsite.repo.RoomRepository;
import com.example.touristWebsite.service.impl.FileStorageService;
import com.example.touristWebsite.service.impl.RoomImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class RoomImageServiceImpl implements RoomImageService {
    @Autowired
    private RoomImageRepository roomImageRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public String uploadImage(Long id, MultipartFile file) {
        try {
            RoomEntity room = roomRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Room not found"));
            String filePath = fileStorageService.saveFile(file);

            RoomImageEntity roomImageEntity = new RoomImageEntity();
            roomImageEntity.setUrl(filePath);
            roomImageEntity.setRoom(room);
            roomImageRepository.save(roomImageEntity);
            return filePath;

        } catch (Exception e) {
                throw new RuntimeException(e);
        }

    }
}
