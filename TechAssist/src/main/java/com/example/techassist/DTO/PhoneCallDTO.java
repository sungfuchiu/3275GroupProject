package com.example.techassist.DTO;

import com.example.techassist.Entities.Client;
import com.example.techassist.Entities.Technician;
import com.example.techassist.Entities.TimeSlot;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PhoneCallDTO {
    public String startDate;
    public String startTime;
    public BigDecimal cost;
    public int durationSlot;
    public int startSlot;
    public long technicianId;
}
