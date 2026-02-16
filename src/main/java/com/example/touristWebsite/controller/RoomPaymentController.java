package com.example.touristWebsite.controller;

import com.example.touristWebsite.dto.RoomPaymentDTO;
import com.example.touristWebsite.dto.RoomPaymentResponseDTO;
import com.example.touristWebsite.entity.UserEntity;
import com.example.touristWebsite.service.impl.RoomPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment/rooms")
public class RoomPaymentController {
    private RoomPaymentService roomPaymentService;

    public RoomPaymentController(RoomPaymentService roomPaymentService) {
        this.roomPaymentService = roomPaymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<RoomPaymentResponseDTO> CreateRoomPayment(@RequestBody RoomPaymentDTO roomPaymentDTO) {
        RoomPaymentResponseDTO payement = roomPaymentService.createNewPayment(roomPaymentDTO);
        if (payement != null) {
            return ResponseEntity.ok(payement);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<RoomPaymentResponseDTO>> getAllRoomPayments() {
        List<RoomPaymentResponseDTO> payments =  roomPaymentService.getAllPayments();
        if (payments != null) {
            return ResponseEntity.ok(payments);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    // Get the payment by booking ID not from payment ID, this is for clients
    @GetMapping("/{bookingId}/{paymentId}")
    public ResponseEntity<RoomPaymentResponseDTO> getRoomPayments(@PathVariable int bookingId, @PathVariable int paymentId) {
        RoomPaymentResponseDTO payment = roomPaymentService.getBookingPayment(bookingId, paymentId);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{paymentId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<RoomPaymentResponseDTO> getRoomPaymentById(@PathVariable int paymentId) {
        RoomPaymentResponseDTO payment = roomPaymentService.getRoomPayment(paymentId);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }


}
