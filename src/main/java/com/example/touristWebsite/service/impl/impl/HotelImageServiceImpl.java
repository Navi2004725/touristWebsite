package com.example.touristWebsite.service.impl.impl;

import com.example.touristWebsite.dto.GetHotelImageDTO;
import com.example.touristWebsite.dto.HotelImageResponseDTO;
import com.example.touristWebsite.entity.HotelEntity;
import com.example.touristWebsite.entity.HotelImageEntity;
import com.example.touristWebsite.repo.HotelImageRepository;
import com.example.touristWebsite.repo.HotelRepository;
import com.example.touristWebsite.service.impl.CloudinaryService;
import com.example.touristWebsite.service.impl.HotelImageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
public class HotelImageServiceImpl implements HotelImageService {


    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final HotelRepository hotelRepo;
    private final HotelImageRepository imageRepo;


    public HotelImageServiceImpl(
            ModelMapper modelMapper,
            CloudinaryService cloudinaryService,
            HotelRepository hotelRepo,
            HotelImageRepository imageRepo
    ) {
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.hotelRepo = hotelRepo;
        this.imageRepo = imageRepo;
    }



    @Override
    public String uploadImage(int hotelId, MultipartFile file) {

        try {

            HotelEntity hotel = hotelRepo.findById(hotelId)
                    .orElseThrow(() ->
                            new RuntimeException("Hotel not found")
                    );


            // Upload to Cloudinary
            String imageUrl = cloudinaryService.upload(file);


            // Save URL to database
            HotelImageEntity image = new HotelImageEntity();

            image.setUrl(imageUrl);
            image.setHotel(hotel);

            imageRepo.save(image);


            return imageUrl;


        } catch (Exception e) {

            throw new RuntimeException(
                    "Image upload failed",
                    e
            );
        }
    }



    @Override
    public HotelImageResponseDTO deleteImage(int hotelId, int imageId) {

        HotelEntity hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() ->
                        new RuntimeException("Hotel not found")
                );


        HotelImageEntity image = imageRepo.findById(imageId)
                .orElseThrow(() ->
                        new RuntimeException("Image not found")
                );


        if (image.getHotel().getHotelId() != hotel.getHotelId()) {
            throw new RuntimeException(
                    "Image does not belong to this hotel"
            );
        }


        imageRepo.delete(image);


        return new HotelImageResponseDTO(
                image.getImageId(),
                image.getUrl()
        );
    }



    @Override
    public List<GetHotelImageDTO> getAllImages() {


        List<HotelImageEntity> hotelImages =
                imageRepo.findAll();


        List<GetHotelImageDTO> addImages =
                new ArrayList<>();


        for (HotelImageEntity hotelImage : hotelImages) {

            GetHotelImageDTO imageDTO =
                    modelMapper.map(
                            hotelImage,
                            GetHotelImageDTO.class
                    );

            addImages.add(imageDTO);
        }


        return addImages;
    }
}