package com.example.techassist.Repositories;

import com.example.techassist.Entities.PhoneCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PhoneCallRepository extends JpaRepository<PhoneCall,Long> {
    Optional<PhoneCall> findById(Long id);
    List<PhoneCall> findByTechnicianIdAndStartTimeGreaterThanEqual(Long technicianId, LocalDateTime now);
    List<PhoneCall> findByTechnicianIdAndStartTimeLessThanEqual(Long technicianId, LocalDateTime now);
}
