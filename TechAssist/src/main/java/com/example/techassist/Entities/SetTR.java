package com.example.techassist.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
//Using Lombok, there is no need to create setters and getters
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class SetTR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String time;
    private String services;
    private String duration;
    private float rate;
}

