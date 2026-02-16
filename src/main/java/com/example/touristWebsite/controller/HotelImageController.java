package com.example.touristWebsite.controller;

import com.example.touristWebsite.entity.HotelEntity;
import com.example.touristWebsite.entity.HotelImageEntity;
import com.example.touristWebsite.repo.HotelImageRepository;
import com.example.touristWebsite.repo.HotelRepository;
import com.example.touristWebsite.service.impl.FileStorageService;
import com.example.touristWebsite.service.impl.HotelImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/hotels")
public class HotelImageController {

    private HotelImageService hotelImageService;

    public HotelImageController(HotelImageService hotelImageService) {
        this.hotelImageService = hotelImageService;
    }

    @PostMapping("/{hotelId}/uploadImage")
    @PreAuthorize("hasRole('admin')") // Only admin can create hotels images
    public ResponseEntity<String> uploadImage(@PathVariable int hotelId,
                                              @RequestParam("file") MultipartFile file) {

            String filePath = hotelImageService.uploadImage(hotelId, file);
            if (filePath != null) {
                return ResponseEntity.ok("Image uploaded successfully: " + filePath);
            }else {
                return ResponseEntity.badRequest().body("Upload failed:");
            }

    }
}
