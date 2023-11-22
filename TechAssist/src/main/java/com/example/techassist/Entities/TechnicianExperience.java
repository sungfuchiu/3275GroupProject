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
public class TechnicianExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String experience_description;
    private int start_year;
    private int year;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "technician_id", nullable = false)
    private Technician technician;
}
