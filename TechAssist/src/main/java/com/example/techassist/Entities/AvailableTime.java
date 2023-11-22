package com.example.techassist.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AvailableTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer week_day;
    private Integer hour;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "duration_slot", referencedColumnName = "id")
    private TimeSlot duration_slot;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "start_slot", referencedColumnName = "id")
    private TimeSlot start_slot;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "technician_id", nullable = false)
    private Technician technician;
}
