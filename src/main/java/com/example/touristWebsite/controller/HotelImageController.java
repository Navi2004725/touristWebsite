package com.example.touristWebsite.controller;

import com.example.touristWebsite.dto.GetHotelImageDTO;
import com.example.touristWebsite.dto.HotelImageResponseDTO;
import com.example.touristWebsite.service.impl.HotelImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping("/hotelImages")
    public ResponseEntity<List<GetHotelImageDTO>> getAllImages() {
        List<GetHotelImageDTO> images = hotelImageService.getAllImages();
        if (images != null) {
            return ResponseEntity.ok(images);
        }else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{hotelId}/{imageId}/deleteHtlImg")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<HotelImageResponseDTO> deleteHotelImage(@PathVariable int hotelId, @PathVariable int imageId) {
        HotelImageResponseDTO hotelImageResponseDTO = hotelImageService.deleteImage(hotelId, imageId);
        if(hotelImageResponseDTO != null) {
            return ResponseEntity.ok(hotelImageResponseDTO);
        }
        return ResponseEntity.badRequest().build();
    }
}
