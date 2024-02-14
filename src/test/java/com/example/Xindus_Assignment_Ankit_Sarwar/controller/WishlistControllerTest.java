package com.example.Xindus_Assignment_Ankit_Sarwar.controller;

import com.example.Xindus_Assignment_Ankit_Sarwar.dto.AuthRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.example.Xindus_Assignment_Ankit_Sarwar.model.User;
import com.example.Xindus_Assignment_Ankit_Sarwar.model.WishlistItem;
import com.example.Xindus_Assignment_Ankit_Sarwar.service.JwtService;
import com.example.Xindus_Assignment_Ankit_Sarwar.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WishlistControllerTest { // go to controller->WishlistController then click ctr+shift+t then it is created automatically

    // directly run class beacuse if we run individually test the method it fails beacause of authToken is null when we individually eun each
//    test case it showing null so registerUser and logInUser except all required token

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private static String authToken;


    @Test
    @Order(1)
    void registerUser() throws Exception {
        // every time change name and email id because it store in database so change emailId it can not store duplicate
        User user = new User( 1L,"suraj", "123", "suraj@gmail.com", "9370215465");
//        User user = new User( "ankit", "123", "ankit@gmail.com", "9370215465");
        mockMvc.perform(post("/api/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User registered successfully")));
    }


    @Test
    @Order(2)
    void logInUser() throws Exception {
        AuthRequest authRequest = new AuthRequest("suraj", "123");
        String response = mockMvc.perform(post("/api/logIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println("Login Response: " + response);

// Extract the token from the response and store it
        authToken = response;
        System.out.println("Token --> " + authToken);
        // Perform login and store the authentication token
        assertTrue(authToken.startsWith("ey"));
    }



    @Test
    @Order(3)
    void createWishlistItem() throws Exception {
        // Create a WishlistItem object
        WishlistItem wishlistItem = new WishlistItem( 1L,"nokia 15", 20);

        // Perform a POST request to create the wishlist item
        mockMvc.perform(post("/api/wishlists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wishlistItem))
                        .param("token", authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())  // Assuming your response DTO has an 'id' field
                .andExpect(jsonPath("$.itemName").value("nokia 15"))
                .andExpect(jsonPath("$.price").value(20))
                .andExpect(jsonPath("$.user.username").exists());  // Assuming your response DTO has a 'user' field with 'username'

    }

    @Test
    @Order(4)
    void getUserWishlist() throws Exception {
        System.out.println(authToken);  // If error showing null then run full class beacause of authToken everytime go to null
        // Add this assertion at the end of logInUser test
        assertNotNull(authToken, "Authentication token should not be null");

        mockMvc.perform(get("/api/wishlists/{username}", "suraj")
                        .param("token", authToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
//                .andExpect(/* Add more assertions based on your expected response */);
    }


//    @Test
//    @Order(5)
//    void deleteWishlistItem() throws Exception {
//        // Ensure that authToken is not null or empty
////        assertNotNull(authToken, "Authentication token should not be null");
//        String token ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXJhaiIsImlhdCI6MTcwNzkxOTEyMiwiZXhwIjoxNzA3OTIwOTIyfQ.vZTqfFllmJOToiYWzxc8Fji2x6ep4TAkxiGDttY5ebI";
//        // Perform login and store the authentication token
//        mockMvc.perform(delete("/api/wishlists/{id}", 2)
//                        .param("token", token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string(equalTo("Wishlist item deleted successfully")));
//    }


    @Test
    @Order(5)
    void deleteWishlistItem() throws Exception {
        // Ensure that authToken is not null or empty
        assertNotNull(authToken, "Authentication token should not be null");

        // Perform login and store the authentication token
        mockMvc.perform(delete("/api/wishlists/{id}", 1)  // if this tet case not pass then first check in database id is present or not
                        .param("token", authToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Wishlist item deleted successfully")));
    }

}
