package com.example.techassist.Repositories;

import com.example.techassist.Entities.AvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvailableTimeRepository extends JpaRepository<AvailableTime,Long> {
    Optional<AvailableTime> findById(Long id);
}
