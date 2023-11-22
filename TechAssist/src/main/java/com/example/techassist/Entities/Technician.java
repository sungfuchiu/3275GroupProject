package com.example.techassist.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//Using Lombok, there is no need to create setters and getters
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Technician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String job_description;
    private Float rate;
    private String image_url;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_field_id", nullable = false)
    private ServiceField serviceField;

    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TechnicianExperience> experiences;

}
