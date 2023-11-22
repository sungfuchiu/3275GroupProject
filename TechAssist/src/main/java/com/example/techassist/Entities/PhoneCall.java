package com.example.techassist.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PhoneCall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime start_time;
    private Integer rating;
    private String review;
    private Float cost;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "duration_slot", referencedColumnName = "id")
    private TimeSlot duration_slot;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "start_slot", referencedColumnName = "id")
    private TimeSlot start_slot;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "technician_id", nullable = false)
    private Technician technician;
}
