package com.example.techassist.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AvailableTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;
    @Column(name = "start_hour", nullable = false)
    private int startHour;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "start_slot", referencedColumnName = "id")
    private TimeSlot start_slot;
    @Column(name = "end_hour", nullable = false)
    private int endHour;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "end_slot", referencedColumnName = "id")
    private TimeSlot end_slot;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "technician_id", nullable = false)
    private Technician technician;
}
