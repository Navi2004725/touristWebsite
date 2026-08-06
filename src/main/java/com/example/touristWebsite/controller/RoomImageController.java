package com.example.touristWebsite.controller;
import com.example.touristWebsite.service.impl.RoomImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rooms")
public class RoomImageController {
    private RoomImageService roomImageService;

    public RoomImageController(RoomImageService roomImageService) {
        this.roomImageService = roomImageService;
    }

    @PostMapping("/{id}/uploadImage")
    @PreAuthorize("hasRole('admin')") // Only admin can create hotels images
    public ResponseEntity<String> uploadRoomImage(@PathVariable Long id,@RequestParam("file") MultipartFile file) {
        String filePath = roomImageService.uploadImage(id, file);
        if (filePath != null) {
            return ResponseEntity.ok("Image uploaded successfully" + filePath);
        }else {
            return ResponseEntity.badRequest().body("Image not uploaded");
        }
    }

    @DeleteMapping("/deleteRoomImage/{imageId}/{roomId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> deleteRoomImg(@PathVariable int imageId, @PathVariable Long roomId) {
        String deletedFile = roomImageService.deleteRoomImage(imageId, roomId);

        if(deletedFile != null){
            return ResponseEntity.ok("Image deleted successfully: " + deletedFile);
        }
        return ResponseEntity.badRequest().body("Image not deleted");
    }



}
