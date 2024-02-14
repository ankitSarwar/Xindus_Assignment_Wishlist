package com.example.Xindus_Assignment_Ankit_Sarwar.service;

import com.example.Xindus_Assignment_Ankit_Sarwar.repo.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistItemService {

    @Autowired
    WishlistItemRepository wishlistItemRepository;

    public void delete(Long id) {
        wishlistItemRepository.deleteById(id);
    }

}
