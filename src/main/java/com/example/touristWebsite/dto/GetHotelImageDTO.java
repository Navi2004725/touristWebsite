package com.example.touristWebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetHotelImageDTO {
    private int hotelId;
    private String url;
}
