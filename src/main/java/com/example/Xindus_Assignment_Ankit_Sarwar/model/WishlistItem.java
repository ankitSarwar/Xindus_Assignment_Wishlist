package com.example.Xindus_Assignment_Ankit_Sarwar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private int price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public WishlistItem(String itemName, int price) {
        this.itemName = itemName;
        this.price = price;
    }

    public WishlistItem(Long id, String itemName, int price) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
    }
}
