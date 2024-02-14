package com.example.Xindus_Assignment_Ankit_Sarwar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistItemResponseDTO {
    private Long id;
    private String itemName;
    private int price;
    private UserResponseDTO user;
}
