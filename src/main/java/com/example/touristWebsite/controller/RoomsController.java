package com.example.touristWebsite.controller;

import com.example.touristWebsite.dto.RoomDTO;
import com.example.touristWebsite.dto.RoomResponseDTO;
import com.example.touristWebsite.service.impl.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomsController {
    private RoomService roomService;

    public RoomsController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO roomDTO) {
       RoomDTO created_room = roomService.createRoom(roomDTO);
       if (created_room != null) {
           return ResponseEntity.ok(created_room);
       }else {
           return ResponseEntity.badRequest().build();
       }

    }
    @GetMapping("/")
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
        List<RoomResponseDTO> rooms = roomService.getAllHotelRooms();
        if (rooms != null) {
            return ResponseEntity.ok(rooms);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO>  getRoomById(@PathVariable Long id) {
        RoomResponseDTO room = roomService.getRoomDetails(id);
        if (room != null) {
            return ResponseEntity.ok(room);

        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomResponseDTO>>  getRoomById(@PathVariable int hotelId) {
        List<RoomResponseDTO> room = roomService.getRoomByHotel(hotelId);
        if (room != null) {
            return ResponseEntity.ok(room);

        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")

    public ResponseEntity<RoomResponseDTO> updateRoom(@RequestBody RoomDTO roomDTO, @PathVariable Long id) {
       RoomResponseDTO updated_room = roomService.updateRoomDetails(roomDTO, id);
       if (updated_room != null) {
           return ResponseEntity.ok(updated_room);
       }else {
           return ResponseEntity.badRequest().build();
       }

    }

    @GetMapping("/search/{hotelId}/{type}")
    public ResponseEntity<List<RoomResponseDTO>>  searchRoomsByType(@PathVariable int hotelId,@PathVariable String type) {
        List<RoomResponseDTO> room = roomService.searchRoomsByType(hotelId, type);
        if (room != null) {
            return ResponseEntity.ok(room);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<RoomResponseDTO> deleteRoom(@PathVariable Long id) {
        RoomResponseDTO deleted_room = roomService.deleteExistedRoom(id);
        if (deleted_room != null) {
            return ResponseEntity.ok(deleted_room);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
}
