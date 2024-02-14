package com.example.Xindus_Assignment_Ankit_Sarwar.controller;

import com.example.Xindus_Assignment_Ankit_Sarwar.dto.AuthRequest;
import com.example.Xindus_Assignment_Ankit_Sarwar.dto.UserResponseDTO;
import com.example.Xindus_Assignment_Ankit_Sarwar.dto.WishlistItemResponseDTO;
import com.example.Xindus_Assignment_Ankit_Sarwar.exception.ErrorResponse;
import com.example.Xindus_Assignment_Ankit_Sarwar.exception.RegistrationException;
import com.example.Xindus_Assignment_Ankit_Sarwar.model.User;
import com.example.Xindus_Assignment_Ankit_Sarwar.model.WishlistItem;
import com.example.Xindus_Assignment_Ankit_Sarwar.repo.UserRepository;
import com.example.Xindus_Assignment_Ankit_Sarwar.repo.WishlistItemRepository;
import com.example.Xindus_Assignment_Ankit_Sarwar.service.JwtService;
import com.example.Xindus_Assignment_Ankit_Sarwar.service.UserService;
import com.example.Xindus_Assignment_Ankit_Sarwar.service.WishlistItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class WishlistController {

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    WishlistItemService wishlistItemService;

    @Autowired
    JwtService jwtService;


    @PostMapping("/signUp")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);


            return ResponseEntity.ok("User registered successfully.");
        } catch (RegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed.");
        }
    }

    @PostMapping("/logIn")
    public ResponseEntity<String> logInUser(@RequestBody AuthRequest authRequest) {
        try {
            // Check if the username exists in the database before attempting authentication
            if (!userService.userExists(authRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }
            // If authentication is successful, generate and return JWT token
            String token = jwtService.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            // Authentication failed due to incorrect username or password
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

        } catch (Exception e) {
            // Handle other authentication exceptions with a generic message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }



    @GetMapping("/wishlists/{username}")
    public ResponseEntity<List<WishlistItemResponseDTO>> getUserWishlist(@PathVariable String username, @RequestParam String token) throws ErrorResponse {
        List<WishlistItemResponseDTO> responseDTOs = userService.getUserWishlist(username, token);
        return ResponseEntity.ok(responseDTOs);
    }


    @PostMapping("/wishlists")
    public WishlistItemResponseDTO createWishlistItem(@RequestBody WishlistItem wishlistItem, @RequestParam String token) {
        try {
            // Extract the username from the token
            String username = jwtService.extractUsername(token);

            // Retrieve the user from the database based on the extracted username
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

            // Set the user for the wishlist item and save it
            wishlistItem.setUser(user);
            WishlistItem savedWishlistItem = wishlistItemRepository.save(wishlistItem);

            // Create a response DTO with only the required fields
            UserResponseDTO userResponseDTO = new UserResponseDTO(savedWishlistItem.getUser().getUsername());
            return new WishlistItemResponseDTO(
                    savedWishlistItem.getId(),
                    savedWishlistItem.getItemName(),
                    savedWishlistItem.getPrice(),
                    userResponseDTO
            );
        } catch (Exception e) {
            // Handle any exceptions (e.g., token validation failure)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized", e);
        }
    }


    @DeleteMapping("/wishlists/{id}")
    public ResponseEntity<?> deleteWishlistItem(@PathVariable Long id, @RequestParam String token) {
        try {
            String username = jwtService.extractUsername(token);
            // Retrieve the user from the database based on the extracted username
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
            Optional<WishlistItem> optionalWishlistItem = wishlistItemRepository.findById(id);

            // Check if the usernames match before proceeding
            if (optionalWishlistItem.isPresent() && optionalWishlistItem.get().getUser().getUsername().equals(username)) {
                wishlistItemService.delete(id);
                return ResponseEntity.ok("Wishlist item deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
            }
        } catch (Exception e) {
            // Handle any exceptions (e.g., token validation failure)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

}