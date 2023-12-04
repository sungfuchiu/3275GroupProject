package com.example.techassist.Repositories;

import com.example.techassist.Entities.PhoneCall;
import com.example.techassist.Entities.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot,Long> {
    Optional<TimeSlot> findById(Long id);
    Optional<TimeSlot> findTimeSlotByDuration(int timeSlot);
    List<TimeSlot> findTimeSlotByIdLessThanEqual(int inHourSlotsId);
    List<TimeSlot> findTimeSlotByIdGreaterThanEqual(int durationSlotsId);
}
