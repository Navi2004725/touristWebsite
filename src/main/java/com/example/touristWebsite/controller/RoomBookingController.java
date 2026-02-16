package com.example.touristWebsite.controller;

import com.example.touristWebsite.dto.RoomBookingDTO;
import com.example.touristWebsite.dto.RoomBookingResponseDTO;
import com.example.touristWebsite.dto.RoomDTO;
import com.example.touristWebsite.service.impl.RoomBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class RoomBookingController {
    private final RoomBookingService roomBookingService;

    public RoomBookingController(RoomBookingService roomBookingService) {
        this.roomBookingService = roomBookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<RoomBookingResponseDTO> createBooking (@RequestBody RoomBookingDTO roomBookingDTO) {
        RoomBookingResponseDTO booking = roomBookingService.addBooking(roomBookingDTO);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        }else  {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/")
    @PreAuthorize("hasRole('admin')") // Only admin can SEE ALL THE BOOKINGS
    public ResponseEntity<List<RoomBookingResponseDTO>> getAllBookings() {
        List<RoomBookingResponseDTO> bookings = roomBookingService.getAllBooks();
        if (bookings != null) {
            return ResponseEntity.ok(bookings);

        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<RoomBookingResponseDTO> getBookingById(@PathVariable int bookingId) {
        RoomBookingResponseDTO booking = roomBookingService.getBookingDetails(bookingId);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoomBookingResponseDTO> updateBooking (@RequestBody RoomBookingDTO roomBookingDTO, @PathVariable int id) {
        RoomBookingResponseDTO updatedBooking = roomBookingService.updateCurrentBooking(roomBookingDTO, id);
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<RoomBookingResponseDTO> deleteBooking (@PathVariable int bookingId) {
        RoomBookingResponseDTO deletedBooking = roomBookingService.deleteCurrentBooking(bookingId);
        if (deletedBooking != null) {
            return ResponseEntity.ok(deletedBooking);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
}
