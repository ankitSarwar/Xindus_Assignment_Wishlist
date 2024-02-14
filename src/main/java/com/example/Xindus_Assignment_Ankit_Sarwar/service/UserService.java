package com.example.Xindus_Assignment_Ankit_Sarwar.service;

import com.example.Xindus_Assignment_Ankit_Sarwar.dto.UserResponseDTO;
import com.example.Xindus_Assignment_Ankit_Sarwar.dto.WishlistItemResponseDTO;
import com.example.Xindus_Assignment_Ankit_Sarwar.exception.ErrorResponse;
import com.example.Xindus_Assignment_Ankit_Sarwar.exception.RegistrationException;
import com.example.Xindus_Assignment_Ankit_Sarwar.model.User;
import com.example.Xindus_Assignment_Ankit_Sarwar.model.WishlistItem;
import com.example.Xindus_Assignment_Ankit_Sarwar.repo.UserRepository;
import com.example.Xindus_Assignment_Ankit_Sarwar.repo.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    WishlistItemService wishlistItemService;

    @Autowired
    JwtService jwtService;

    @Autowired
    WishlistItemRepository wishlistItemRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RegistrationException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RegistrationException("Email address is already registered");
        }

        // Generate verification token
        String token = UUID.randomUUID().toString();

        // Hash and store the password securely
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user
        User registeredUser = userRepository.save(user);

        return registeredUser;
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


    public List<WishlistItemResponseDTO> getUserWishlist(String username, String token) throws ErrorResponse {
        try {
            // Extract the username from the token
            String tokenUsername = jwtService.extractUsername(token);

            // Check if the token username matches the requested username
            if (!username.equals(tokenUsername)) {
                // Throw an exception or handle it based on your business requirements
                throw new AccessDeniedException("Access denied");
            }

            // If the token is valid and matches the requested username, proceed to retrieve the wishlist
            List<WishlistItem> wishlistItems = wishlistItemRepository.findByUserUsername(username);

            // Convert WishlistItem to WishlistItemResponseDTO to include only required fields
            return wishlistItems.stream()
                    .map(item -> new WishlistItemResponseDTO(
                            item.getId(),
                            item.getItemName(),
                            item.getPrice(),
                            new UserResponseDTO(item.getUser().getUsername())
                    ))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            // Handle any exceptions (e.g., token validation failure)
            throw new ErrorResponse("Unauthorized",e);
        }
    }
}