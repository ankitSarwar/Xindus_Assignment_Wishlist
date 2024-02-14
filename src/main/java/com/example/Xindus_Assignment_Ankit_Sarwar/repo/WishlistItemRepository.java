package com.example.Xindus_Assignment_Ankit_Sarwar.repo;

import com.example.Xindus_Assignment_Ankit_Sarwar.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUserUsername(String username);
}
