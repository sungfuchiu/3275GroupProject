package com.example.techassist.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TechnicianCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String certificate_url;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "technician_id", nullable = false)
    private Technician technician;
}
