package com.example.touristWebsite.service.impl.impl;

import com.example.touristWebsite.dto.RoomBookingDTO;
import com.example.touristWebsite.dto.RoomBookingResponseDTO;
import com.example.touristWebsite.dto.RoomPaymentResponseDTO;
import com.example.touristWebsite.entity.HotelEntity;
import com.example.touristWebsite.entity.RoomBookingEntity;
import com.example.touristWebsite.entity.RoomEntity;
import com.example.touristWebsite.entity.RoomPaymentsEntity;
import com.example.touristWebsite.repo.HotelRepository;
import com.example.touristWebsite.repo.RoomBookingRepository;
import com.example.touristWebsite.repo.RoomRepository;
import com.example.touristWebsite.service.impl.RoomBookingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomBookingServiceIMPL implements RoomBookingService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final RoomBookingRepository roomBookingRepository;

    public RoomBookingServiceIMPL(RoomBookingRepository roomBookingRepository, RoomRepository roomRepository, HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.roomBookingRepository = roomBookingRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;


    }
    @Override
    public RoomBookingResponseDTO addBooking(RoomBookingDTO roomBookingDTO) {
        RoomBookingEntity roomBookingEntity = new RoomBookingEntity();
        roomBookingEntity.setName(roomBookingDTO.getName());
        roomBookingEntity.setEmail(roomBookingDTO.getEmail());
        roomBookingEntity.setPhoneNumber(roomBookingDTO.getPhoneNumber());
        roomBookingEntity.setBooked_date(roomBookingDTO.getBooked_date());
        // Load RoomEntity from DB
        RoomEntity room = roomRepository.findById(roomBookingDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room with ID " + roomBookingDTO.getRoomId() + " not found"));
        roomBookingEntity.setRoom(room);

        // Load HotelEntity from DB
        HotelEntity hotel = hotelRepository.findById(roomBookingDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel with ID " + roomBookingDTO.getHotelId() + " not found"));
        roomBookingEntity.setHotel(hotel);

        if(roomBookingDTO.getPayment() != null) {
            roomBookingDTO.getPayment().forEach(payment -> payment.setBookingId(roomBookingEntity));
            roomBookingEntity.setRoomPayments(roomBookingDTO.getPayment());
        }

        roomBookingRepository.save(roomBookingEntity);
        return getRoomBookingDTO(roomBookingEntity);
    }

    @Override
    public List<RoomBookingResponseDTO> getAllBooks() {
        List<RoomBookingEntity> roomBookingEntities = roomBookingRepository.findAll();
        List<RoomBookingResponseDTO> roomBookingResponseDTOs = new ArrayList<>();
        if (roomBookingEntities != null) {
            for (RoomBookingEntity roomBookingEntity : roomBookingEntities) {
                roomBookingResponseDTOs.add(getRoomBookingDTO(roomBookingEntity));
            }
        }
        return roomBookingResponseDTOs;

    }

    @Override
    public RoomBookingResponseDTO getBookingDetails(int bookingId) {
        RoomBookingEntity roomBookingEntity = roomBookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("booking with ID " + bookingId + " not found"));
        return getRoomBookingDTO(roomBookingEntity);
    }

    @Override
    public RoomBookingResponseDTO updateCurrentBooking(RoomBookingDTO roomBookingDTO, int id) {
        RoomBookingEntity roomBookingEntity = roomBookingRepository.findById(id).orElseThrow(() -> new RuntimeException("booking with ID " + id + " not found"));
        roomBookingEntity.setName(roomBookingDTO.getName());
        roomBookingEntity.setEmail(roomBookingDTO.getEmail());
        roomBookingEntity.setPhoneNumber(roomBookingDTO.getPhoneNumber());
        roomBookingEntity.setBooked_date(roomBookingDTO.getBooked_date());
        // Load RoomEntity from DB
        RoomEntity room = roomRepository.findById(roomBookingDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room with ID " + roomBookingDTO.getRoomId() + " not found"));
        roomBookingEntity.setRoom(room);

        // Load HotelEntity from DB
        HotelEntity hotel = hotelRepository.findById(roomBookingDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel with ID " + roomBookingDTO.getHotelId() + " not found"));
        roomBookingEntity.setHotel(hotel);

        if(roomBookingDTO.getPayment() != null) {
            roomBookingDTO.getPayment().forEach(payment -> payment.setBookingId(roomBookingEntity));
            roomBookingEntity.setRoomPayments(roomBookingDTO.getPayment());
        }

        roomBookingRepository.save(roomBookingEntity);
        return getRoomBookingDTO(roomBookingEntity);

    }

    @Override
    public RoomBookingResponseDTO deleteCurrentBooking(int bookingId) {
        RoomBookingEntity booked = roomBookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("booking with ID " + bookingId + " not found"));
        roomBookingRepository.delete(booked);
        return getRoomBookingDTO(booked);
    }

    public List<RoomPaymentResponseDTO> getRoomBookings(RoomBookingEntity roomBookingEntity) {
        List<RoomPaymentResponseDTO> roomPaymentResponseDTOS = new ArrayList<>();
        for (RoomPaymentsEntity roomPaymentsEntity : roomBookingEntity.getRoomPayments()) {
            roomPaymentResponseDTOS.add(modelMapper.map(roomPaymentsEntity, RoomPaymentResponseDTO.class));
        }
        return roomPaymentResponseDTOS;
    }

    public RoomBookingResponseDTO getRoomBookingDTO(RoomBookingEntity roomBookingEntity) {
        RoomBookingResponseDTO roomBookingResponseDTO = new RoomBookingResponseDTO();
        roomBookingResponseDTO.setBookingId(roomBookingEntity.getBookingId());
        roomBookingResponseDTO.setName(roomBookingEntity.getName());
        roomBookingResponseDTO.setEmail(roomBookingEntity.getEmail());
        roomBookingResponseDTO.setPhoneNumber(roomBookingEntity.getPhoneNumber());
        roomBookingResponseDTO.setBooked_date(roomBookingEntity.getBooked_date());
        roomBookingResponseDTO.setHotelId(roomBookingEntity.getHotel().getHotelId());
        roomBookingResponseDTO.setRoomId(roomBookingEntity.getRoom().getRoomId());
        roomBookingResponseDTO.setPayments(getRoomBookings(roomBookingEntity));

        return roomBookingResponseDTO;


    }
}
