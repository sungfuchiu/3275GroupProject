package com.example.techassist.Repositories;

import com.example.techassist.Entities.PhoneCall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnicianCertificateRepository extends JpaRepository<PhoneCall,Long> {
    Optional<PhoneCall> findById(Long id);
}
