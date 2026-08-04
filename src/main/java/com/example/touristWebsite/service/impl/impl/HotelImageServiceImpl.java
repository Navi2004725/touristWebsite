package com.example.touristWebsite.service.impl.impl;

import com.example.touristWebsite.dto.GetHotelImageDTO;
import com.example.touristWebsite.dto.HotelImageResponseDTO;
import com.example.touristWebsite.entity.HotelEntity;
import com.example.touristWebsite.entity.HotelImageEntity;
import com.example.touristWebsite.repo.HotelImageRepository;
import com.example.touristWebsite.repo.HotelRepository;
import com.example.touristWebsite.service.impl.FileStorageService;
import com.example.touristWebsite.service.impl.HotelImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelImageServiceImpl implements HotelImageService {
    private final ModelMapper modelMapper;

    public HotelImageServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private HotelRepository hotelRepo;

    @Autowired
    private HotelImageRepository imageRepo;

    @Override
    public String uploadImage(int hotelId, MultipartFile file) {
        try {
            HotelEntity hotel = hotelRepo.findById(hotelId)
                    .orElseThrow(() -> new RuntimeException("Hotel not found"));

            // Save file in uploads folder
            String filePath = fileStorageService.saveFile(file);

            // Save image entity in DB
            HotelImageEntity image = new HotelImageEntity();
            image.setUrl(filePath);
            image.setHotel(hotel);
            imageRepo.save(image);
            return filePath;
        } catch (Exception e) {
                throw new RuntimeException(e);
        }

    }

    @Override
    public HotelImageResponseDTO deleteImage(int hotelId, int imageId) {
        HotelEntity hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        HotelImageEntity image = imageRepo.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        if (image.getHotel().getHotelId() != (hotel.getHotelId())) {
            throw new RuntimeException("Image does not belong to the specified hotel");
        }

        imageRepo.delete(image);

        return new HotelImageResponseDTO(image.getImageId(), image.getUrl());
    }

    @Override
    public List<GetHotelImageDTO> getAllImages() {
        List<HotelImageEntity> hotelImages = imageRepo.findAll();
        List<GetHotelImageDTO> addImages = new ArrayList<>();
        if(hotelImages != null) {
            for (HotelImageEntity hotelImageEntity : hotelImages) {
                GetHotelImageDTO imageDTO = modelMapper.map(hotelImageEntity, GetHotelImageDTO.class);
                addImages.add(imageDTO);
            }
            return addImages;
        }
        return null;
    }
}
