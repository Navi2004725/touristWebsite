package com.example.touristWebsite.service.impl.impl;

import com.example.touristWebsite.entity.RoomEntity;
import com.example.touristWebsite.entity.RoomImageEntity;
import com.example.touristWebsite.repo.RoomImageRepository;
import com.example.touristWebsite.repo.RoomRepository;
import com.example.touristWebsite.service.impl.CloudinaryService;
import com.example.touristWebsite.service.impl.RoomImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class RoomImageServiceImpl implements RoomImageService {


    private final RoomImageRepository roomImageRepository;
    private final RoomRepository roomRepository;
    private final CloudinaryService cloudinaryService;



    public RoomImageServiceImpl(
            RoomImageRepository roomImageRepository,
            RoomRepository roomRepository,
            CloudinaryService cloudinaryService
    ) {
        this.roomImageRepository = roomImageRepository;
        this.roomRepository = roomRepository;
        this.cloudinaryService = cloudinaryService;
    }



    @Override
    public String uploadImage(Long id, MultipartFile file) {

        try {

            RoomEntity room =
                    roomRepository.findById(id)
                            .orElseThrow(() ->
                                    new RuntimeException("Room not found")
                            );


            // Upload image to Cloudinary
            String imageUrl =
                    cloudinaryService.upload(file);



            RoomImageEntity roomImage =
                    new RoomImageEntity();


            roomImage.setUrl(imageUrl);
            roomImage.setRoom(room);


            roomImageRepository.save(roomImage);


            return imageUrl;


        } catch (Exception e) {

            throw new RuntimeException(
                    "Image upload failed",
                    e
            );
        }
    }
}