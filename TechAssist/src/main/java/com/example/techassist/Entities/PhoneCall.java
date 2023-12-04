package com.example.techassist.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PhoneCall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    private Integer rating;
    private String review;
    private BigDecimal cost;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "duration_slot", referencedColumnName = "id")
    private TimeSlot durationSlot;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "start_slot", referencedColumnName = "id")
    private TimeSlot startSlot;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "technician_id", nullable = false)
    private Technician technician;
}
