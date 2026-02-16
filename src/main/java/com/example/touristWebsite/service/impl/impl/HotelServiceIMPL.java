package com.example.touristWebsite.service.impl.impl;

import com.example.touristWebsite.dto.*;
import com.example.touristWebsite.entity.HotelEntity;
import com.example.touristWebsite.entity.HotelImageEntity;
import com.example.touristWebsite.entity.RoomBookingEntity;
import com.example.touristWebsite.entity.RoomEntity;
import com.example.touristWebsite.repo.HotelRepository;
import com.example.touristWebsite.repo.UserRepo;
import com.example.touristWebsite.service.impl.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelServiceIMPL implements HotelService {
    private final HotelRepository hotelRepository;
    private ModelMapper modelMapper;
    private UserRepo userRepo;


    public HotelServiceIMPL(HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public HotelResponseDTO addHotel(HotelDTO hotelDTO) {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setHotelName(hotelDTO.getHotelName());
        hotelEntity.setHotelAddress(hotelDTO.getHotelAddress());
        hotelEntity.setRating(hotelDTO.getRating());
        hotelEntity.setEmail(hotelDTO.getEmail());
        hotelEntity.setPhone(hotelDTO.getPhone());
        hotelEntity.setDescription(hotelDTO.getDescription());
        // Set rooms safely
        if (hotelDTO.getRooms() != null) {
            hotelDTO.getRooms().forEach(room -> room.setHotel(hotelEntity));
            hotelEntity.setRooms(hotelDTO.getRooms());
        }

        // Set images safely (if you add them later)
        if (hotelDTO.getHotel_images() != null) {
            hotelDTO.getHotel_images().forEach(img -> img.setHotel(hotelEntity));
            hotelEntity.setHotel_images(hotelDTO.getHotel_images());
        }

        if (hotelDTO.getBookings() != null) {
            hotelDTO.getBookings().forEach(booking -> booking.setHotel(hotelEntity));
            hotelEntity.setBookings(hotelDTO.getBookings());
        }
        HotelEntity createdHotel = hotelRepository.save(hotelEntity);

        return getHotelResponseDTO(createdHotel);

    }

    @Override
    public List<HotelResponseDTO> getAllHotels() {
        List<HotelEntity> hotelEntities = hotelRepository.findAll();
        List<HotelResponseDTO> hotelResponseDTOs = new ArrayList<>();
        for (HotelEntity hotelEntity : hotelEntities) {
            hotelResponseDTOs.add(getHotelResponseDTO(hotelEntity));
        }
        return hotelResponseDTOs;

    }

    @Override
    public HotelResponseDTO getHotelDetails(int id) {
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel with ID " + id + " not found"));;
        return getHotelResponseDTO(hotel);
    }

    @Override
    public HotelDTO updateHotelInfo(HotelDTO hotelDTO, int id) {
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel with ID " + id + " not found"));;
        hotel.setHotelName(hotelDTO.getHotelName());
        hotel.setHotelAddress(hotelDTO.getHotelAddress());
        hotel.setRating(hotelDTO.getRating());
        hotel.setEmail(hotelDTO.getEmail());
        hotel.setPhone(hotelDTO.getPhone());
        hotel.setDescription(hotelDTO.getDescription());
        if (hotelDTO.getRooms() != null) {
            hotelDTO.getRooms().forEach(room -> room.setHotel(hotel));
            hotel.setRooms(hotelDTO.getRooms());
        }
        if (hotelDTO.getHotel_images() != null) {
            hotelDTO.getHotel_images().forEach(img -> img.setHotel(hotel));
            hotel.setHotel_images(hotelDTO.getHotel_images());
        }

        if (hotelDTO.getBookings() != null) {
            hotelDTO.getBookings().forEach(booking -> booking.setHotel(hotel));
            hotel.setBookings(hotelDTO.getBookings());
        }
        HotelEntity updatedHotel = hotelRepository.save(hotel);
        return modelMapper.map(updatedHotel, HotelDTO.class);
    }

    @Override
    public HotelDTO hotelDelete(int id) {
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel with ID " + id + " not found"));
        hotelRepository.delete(hotel);
        return modelMapper.map(hotel, HotelDTO.class);
    }

    @Override
    public List<HotelResponseDTO> searchHotelByAddress(String keyword) {
        List<HotelEntity> hotels = hotelRepository.findByHotelAddressContainingIgnoreCase(keyword);
        if (hotels.isEmpty()) {
            throw new RuntimeException("No hotels found with address containing: " + keyword);
        }
        List<HotelResponseDTO> hotelResponseDTOs = new ArrayList<>();
        for(HotelEntity hotel : hotels) {
            hotelResponseDTOs.add(getHotelResponseDTO(hotel));
        }
        return hotelResponseDTOs;

    }

    private List<HotelImageResponseDTO> getHotelImages(HotelEntity hotel) {
        List<HotelImageResponseDTO> hotelImageDTOs = new ArrayList<>();
        if (hotel.getHotel_images() != null) {
            for (HotelImageEntity img : hotel.getHotel_images()) {
                hotelImageDTOs.add(modelMapper.map(img, HotelImageResponseDTO.class));
            }
        }
        return hotelImageDTOs;
    }


    private List<RoomResponseDTO> getRooms(HotelEntity hotel) {
        List<RoomResponseDTO> roomResponseDTOS = new ArrayList<>();
        if (hotel.getRooms() != null) {
            for (RoomEntity room : hotel.getRooms()) {
                RoomResponseDTO roomResponseDTO = modelMapper.map(room, RoomResponseDTO.class);
                roomResponseDTOS.add(roomResponseDTO);
            }
        }
        return roomResponseDTOS;
    }

    private List<RoomBookingResponseDTO> getBookings(HotelEntity hotel) {
        List<RoomBookingResponseDTO> bookingResponseDTOS = new ArrayList<>();
        if (hotel.getBookings() != null) {
            for (RoomBookingEntity booking : hotel.getBookings()) {
                RoomBookingResponseDTO bookingResponseDTO = modelMapper.map(booking, RoomBookingResponseDTO.class);
                bookingResponseDTOS.add(bookingResponseDTO);
            }
        }
        return bookingResponseDTOS;
    }


    private HotelResponseDTO getHotelResponseDTO(HotelEntity hotel) {
        HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();
        hotelResponseDTO.setHotelId(hotel.getHotelId());
        hotelResponseDTO.setHotelName(hotel.getHotelName());
        hotelResponseDTO.setHotelAddress(hotel.getHotelAddress());
        hotelResponseDTO.setRating(hotel.getRating());
        hotelResponseDTO.setEmail(hotel.getEmail());
        hotelResponseDTO.setPhone(hotel.getPhone());
        hotelResponseDTO.setDescription(hotel.getDescription());

        // Pass the current hotel to get images/rooms
        hotelResponseDTO.setImages(getHotelImages(hotel));
        hotelResponseDTO.setRooms(getRooms(hotel));
        hotelResponseDTO.setBookings(getBookings(hotel));


        return hotelResponseDTO;

    }


}
