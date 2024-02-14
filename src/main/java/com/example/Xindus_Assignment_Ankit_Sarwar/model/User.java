package com.example.Xindus_Assignment_Ankit_Sarwar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    public User(String username, String password, String email, String phone_no) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone_no = phone_no;
    }

    private String phone_no;



}
