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
    public Technician(String job_description, Float rate, String image_url, ServiceField serviceField){
        this.job_description = job_description;
        this.rate = rate;
        this.image_url = image_url;
        this.serviceField = serviceField;
        this.certificates = new ArrayList<>();
        this.experiences =  new ArrayList<>();
        this.availableTimes = new ArrayList<>();
        this.phoneCalls = new ArrayList<>();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String job_description;
    private Float rate;
    private String image_url;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_field_id", nullable = false)
    private ServiceField serviceField;

    @OneToMany(mappedBy = "technician", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TechnicianCertificate> certificates;

    @OneToMany(mappedBy = "technician", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TechnicianExperience> experiences;

    @OneToMany(mappedBy = "technician", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AvailableTime> availableTimes;

    @OneToMany(mappedBy = "technician", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PhoneCall> phoneCalls;

}