package com.example.touristWebsite.service.impl.impl;

import com.example.touristWebsite.dto.RoomPaymentDTO;
import com.example.touristWebsite.dto.RoomPaymentResponseDTO;
import com.example.touristWebsite.entity.RoomBookingEntity;
import com.example.touristWebsite.entity.RoomPaymentsEntity;
import com.example.touristWebsite.entity.UserEntity;
import com.example.touristWebsite.repo.RoomBookingRepository;
import com.example.touristWebsite.repo.RoomPaymentRepository;
import com.example.touristWebsite.repo.UserRepo;
import com.example.touristWebsite.service.impl.RoomPaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomPaymentServiceIMPL implements RoomPaymentService {
    private final RoomPaymentRepository roomPaymentRepository;
    private final ModelMapper modelMapper;
    private final RoomBookingRepository roomBookingRepository;
    private final UserRepo userRepo;
    public RoomPaymentServiceIMPL(RoomPaymentRepository roomPaymentRepository, ModelMapper modelMapper,
                                  RoomBookingRepository roomBookingRepository, UserRepo userRepo
    ) {
        this.roomPaymentRepository = roomPaymentRepository;
        this.modelMapper = modelMapper;
        this.roomBookingRepository = roomBookingRepository;
        this.userRepo = userRepo;
    }
    @Override
    public RoomPaymentResponseDTO createNewPayment(RoomPaymentDTO roomPaymentDTO) {
        RoomPaymentsEntity room = new RoomPaymentsEntity();
        room.setPaymentId(roomPaymentDTO.getPaymentId());
        room.setAmount(roomPaymentDTO.getAmount());
        room.setPaymentPurpose(roomPaymentDTO.getPaymentPurpose());
        room.setPaymentDateTime(roomPaymentDTO.getPaymentDateTime());
        UserEntity userId = userRepo.findById(roomPaymentDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        room.setUserId(userId);
        RoomBookingEntity bookingId = roomBookingRepository.findById(roomPaymentDTO.getBookingId()).orElseThrow(() -> new RuntimeException("Booking not found"));
        room.setBookingId(bookingId);
        RoomPaymentsEntity roomPayments = roomPaymentRepository.save(room);
        RoomPaymentResponseDTO roomPaymentResponseDTO = modelMapper.map(roomPayments, RoomPaymentResponseDTO.class);
        return roomPaymentResponseDTO;

    }

    @Override
    public List<RoomPaymentResponseDTO> getAllPayments() {
        List<RoomPaymentsEntity> roomPayments = roomPaymentRepository.findAll();
        List<RoomPaymentResponseDTO> roomPaymentResponseDTOs = new ArrayList<>();
        for (RoomPaymentsEntity rooms : roomPayments) {
            roomPaymentResponseDTOs.add(modelMapper.map(rooms, RoomPaymentResponseDTO.class));
        }
        return roomPaymentResponseDTOs;
    }



    @Override
    public RoomPaymentResponseDTO getRoomPayment(int paymentId) {
       RoomPaymentsEntity payment = roomPaymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment not found"));
       return modelMapper.map(payment, RoomPaymentResponseDTO.class);
    }

    @Override
    public RoomPaymentResponseDTO getBookingPayment(int bookingId, int paymentId) {
        RoomBookingEntity booking = roomBookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        for (RoomPaymentsEntity roomPayments : booking.getRoomPayments()) {
            if (roomPayments.getPaymentId() == paymentId) {
                return modelMapper.map(roomPayments, RoomPaymentResponseDTO.class);
            }
        }
        return null;
    }


}
