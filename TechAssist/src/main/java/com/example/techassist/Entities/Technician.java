package com.example.techassist.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

//Using Lombok, there is no need to create setters and getters
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Technician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private int phone;
    private String email;
    private String address;
    private String experience;

    @Lob
    private byte[] certification; // Store image content as bytes
    private byte[] picture;
}
