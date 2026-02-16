package com.example.touristWebsite.controller;

import com.example.touristWebsite.dto.HotelResponseDTO;
import com.example.touristWebsite.dto.HotelDTO;
import com.example.touristWebsite.service.impl.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelsController {
    private HotelService hotelService;

    public HotelsController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('admin')") // Only admin can create hotels
    public ResponseEntity<HotelResponseDTO> addHotels(@RequestBody HotelDTO hotelDTO) {
       HotelResponseDTO createdHotel = hotelService.addHotel(hotelDTO);
       if (createdHotel != null) {
           return ResponseEntity.ok(createdHotel);
       }else {
           return ResponseEntity.badRequest().build();

       }

    }
    @GetMapping("/")
    public ResponseEntity<List<HotelResponseDTO>> getAllHotels() {
        List<HotelResponseDTO> hotels = hotelService.getAllHotels();
        if (hotels != null) {
            return ResponseEntity.ok(hotels);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> getHotelById(@PathVariable int id) {
        HotelResponseDTO hotel = hotelService.getHotelDetails(id);
        if (hotel != null) {
            return ResponseEntity.ok(hotel);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('admin')") // Only admin can create hotels
    public ResponseEntity<HotelDTO> updateHotel(@RequestBody HotelDTO hotelDTO, @PathVariable int id) {
        HotelDTO updated_hotel = hotelService.updateHotelInfo(hotelDTO, id);
        if (updated_hotel != null) {
            return ResponseEntity.ok(updated_hotel);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('admin')") // Only admin can delete hotels
    public ResponseEntity<HotelDTO> deleteHotel(@PathVariable int id) {
       HotelDTO deleted_hotel = hotelService.hotelDelete(id);
       if (deleted_hotel != null) {
           return ResponseEntity.ok(deleted_hotel);
       }else {
           return ResponseEntity.badRequest().build();

       }
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<HotelResponseDTO>> searchHotels(@PathVariable String keyword) {
        List<HotelResponseDTO> hotel = hotelService.searchHotelByAddress(keyword);
        if (hotel != null) {
            return ResponseEntity.ok(hotel);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

}
