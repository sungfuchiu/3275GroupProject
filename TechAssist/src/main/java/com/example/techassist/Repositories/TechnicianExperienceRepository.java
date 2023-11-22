package com.example.techassist.Repositories;

import com.example.techassist.Entities.ServiceField;
import com.example.techassist.Entities.Technician;
import com.example.techassist.Entities.TechnicianExperience;
import com.example.techassist.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface TechnicianExperienceRepository extends JpaRepository<TechnicianExperience,Long> {
    Optional<TechnicianExperience> findById(Long id);
}
