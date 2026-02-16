package com.example.touristWebsite.service.impl.impl;

import com.example.touristWebsite.dto.RoomBookingResponseDTO;
import com.example.touristWebsite.dto.RoomDTO;
import com.example.touristWebsite.dto.RoomImageResponseDTO;
import com.example.touristWebsite.dto.RoomResponseDTO;
import com.example.touristWebsite.entity.HotelEntity;
import com.example.touristWebsite.entity.RoomBookingEntity;
import com.example.touristWebsite.entity.RoomEntity;
import com.example.touristWebsite.entity.RoomImageEntity;
import com.example.touristWebsite.repo.HotelRepository;
import com.example.touristWebsite.repo.RoomRepository;
import com.example.touristWebsite.service.impl.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceIMPL implements RoomService {
    private RoomRepository roomRepository;
    private ModelMapper modelMapper;
    private HotelRepository hotelRepository;

    public RoomServiceIMPL(RoomRepository roomRepository, ModelMapper modelMapper, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
        this.hotelRepository = hotelRepository;
    }
    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        RoomEntity room = new RoomEntity();
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setRoomType(roomDTO.getRoomType());
        room.setCapacity(roomDTO.getCapacity());
        room.setPricePerNight(roomDTO.getPricePerNight());
        room.setAvailable(roomDTO.isAvailable());
        room.setDescription(roomDTO.getDescription());
        room.setHotel(roomDTO.getHotel());
        // Set images safely (if you add them later)
        if (roomDTO.getRoomImages() != null) {
            roomDTO.getRoomImages().forEach(img -> img.setRoom(room));
            room.setRoomImages(roomDTO.getRoomImages());
        }

        if (roomDTO.getRoomBooking() != null) {
            roomDTO.getRoomBooking().forEach(roomBooking -> roomBooking.setRoom(room));
            room.setBookings(roomDTO.getRoomBooking());
        }
        RoomEntity createdRoom = roomRepository.save(room);
        return modelMapper.map(createdRoom, RoomDTO.class);

    }

    @Override
    public List<RoomResponseDTO> getAllHotelRooms() {
        List<RoomEntity> roomEntities = roomRepository.findAll();
        List<RoomResponseDTO> roomResponseDTOList = new ArrayList<>();
        for (RoomEntity roomEntity : roomEntities) {
            roomResponseDTOList.add(getRoomResponseDTO(roomEntity));
        }
        return roomResponseDTOList;

    }

    @Override
    public RoomResponseDTO getRoomDetails(Long id) {
        RoomEntity room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel with ID " + id + " not found"));
        return getRoomResponseDTO(room);
    }

    @Override
    public RoomResponseDTO updateRoomDetails(RoomDTO roomDTO, Long id) {
        RoomEntity room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel with ID " + id + " not found"));
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setRoomType(roomDTO.getRoomType());
        room.setCapacity(roomDTO.getCapacity());
        room.setPricePerNight(roomDTO.getPricePerNight());
        room.setAvailable(roomDTO.isAvailable());
        room.setDescription(roomDTO.getDescription());
        room.setHotel(roomDTO.getHotel());
        if (roomDTO.getRoomImages() != null) {
            roomDTO.getRoomImages().forEach(img -> img.setRoom(room));
            room.setRoomImages(roomDTO.getRoomImages());
        }
        if (roomDTO.getRoomBooking() != null) {
            roomDTO.getRoomBooking().forEach(roomBooking -> {roomBooking.setRoom(room);});
            room.setBookings(roomDTO.getRoomBooking());

        }
        roomRepository.save(room);
        return getRoomResponseDTO(room);
    }

    @Override
    public RoomResponseDTO deleteExistedRoom(Long id) {
        RoomEntity room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel with ID " + id + " not found"));
        roomRepository.delete(room);
        return getRoomResponseDTO(room);
    }

    @Override
    public List<RoomResponseDTO> getRoomByHotel(int hotelId) {
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel with ID " + hotelId + " not found"));
        List<RoomResponseDTO> roomResponseDTOList = new ArrayList<>();
        for(RoomEntity roomEntity : hotel.getRooms()) {
            roomResponseDTOList.add(getRoomResponseDTO(roomEntity));
        }
        return roomResponseDTOList;
    }

    @Override
    public List<RoomResponseDTO> searchRoomsByType(int hotelId, String type) {
        List<RoomEntity> rooms = roomRepository.findByHotel_HotelIdAndRoomTypeContainingIgnoreCase(hotelId, type);
        List<RoomResponseDTO> roomResponseDTOList = new ArrayList<>();
        for (RoomEntity roomEntity : rooms) {
            roomResponseDTOList.add(getRoomResponseDTO(roomEntity));
        }
        return roomResponseDTOList;

    }

    public List<RoomImageResponseDTO> roomImages(RoomEntity roomEntity) {
        List<RoomImageResponseDTO> roomImageResponseDTOS = new ArrayList<>();
        if (roomEntity.getRoomImages() != null) {
            for (RoomImageEntity roomImageEntity : roomEntity.getRoomImages()) {
                roomImageResponseDTOS.add(modelMapper.map(roomImageEntity, RoomImageResponseDTO.class));
            }

        }
        return roomImageResponseDTOS;

    }

    public List<RoomBookingResponseDTO> bookings(RoomEntity roomEntity) {
        List<RoomBookingResponseDTO> roomBookingResponseDTOS = new ArrayList<>();
        if (roomEntity.getBookings() != null) {
            for (RoomBookingEntity roomBookingEntity : roomEntity.getBookings()) {
                roomBookingResponseDTOS.add(modelMapper.map(roomBookingEntity, RoomBookingResponseDTO.class));
            }
        }
        return roomBookingResponseDTOS;
    }
    public RoomResponseDTO getRoomResponseDTO(RoomEntity roomEntity) {
        HotelEntity hotel = roomEntity.getHotel();
        RoomResponseDTO roomResponseDTO = new RoomResponseDTO();
        roomResponseDTO.setRoomId(roomEntity.getRoomId());
        roomResponseDTO.setRoomNumber(roomEntity.getRoomNumber());
        roomResponseDTO.setRoomType(roomEntity.getRoomType());
        roomResponseDTO.setCapacity(roomEntity.getCapacity());
        roomResponseDTO.setPricePerNight(roomEntity.getPricePerNight());
        roomResponseDTO.setAvailable(roomEntity.isAvailable());
        roomResponseDTO.setDescription(roomEntity.getDescription());
        roomResponseDTO.setRoomImages(roomImages(roomEntity));
        roomResponseDTO.setBookings(bookings(roomEntity));
        if(hotel != null) {
            int hotel_id = hotel.getHotelId();
            roomResponseDTO.setHotelId(hotel_id);
        }
        return roomResponseDTO;

    }
}
