package com.example.touristWebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelImageResponseDTO {
    private int imageId;
    private String url;   // Path to image
}
