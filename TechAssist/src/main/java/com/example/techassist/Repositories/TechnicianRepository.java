package com.example.techassist.Repositories;

import com.example.techassist.Entities.ServiceField;
import com.example.techassist.Entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TechnicianRepository extends JpaRepository<Technician,Long>{
    Optional<Technician> findById(Long id);

}