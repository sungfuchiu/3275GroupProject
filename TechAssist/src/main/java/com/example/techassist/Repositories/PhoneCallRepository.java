package com.example.techassist.Repositories;

import com.example.techassist.Entities.PhoneCall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PhoneCallRepository extends JpaRepository<PhoneCall,Long> {
    Optional<PhoneCall> findById(Long id);
    List<PhoneCall> findByTechnicianIdAndStartTimeGreaterThanEqualOrderByStartTime(Long technicianId, LocalDateTime now);
    List<PhoneCall> findByClientIdAndStartTimeGreaterThanEqualOrderByStartTime(Long technicianId, LocalDateTime now);
    List<PhoneCall> findByTechnicianIdAndStartTimeLessThanEqualOrderByStartTime(Long technicianId, LocalDateTime now);
    List<PhoneCall> findByTechnicianIdAndStartTimeLessThanEqualAndStartTimeGreaterThanOrderByStartTime(Long technicianId, LocalDateTime now, LocalDateTime thisMonth);
}
