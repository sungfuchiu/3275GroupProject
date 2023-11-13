package com.example.groupprojecttechnician.Repositories;

import com.example.groupprojecttechnician.Entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface TechnicianRepository extends JpaRepository<Technician,Long>{
    //For searching records
    List<Technician> findTechnicianById (long kw);
}
