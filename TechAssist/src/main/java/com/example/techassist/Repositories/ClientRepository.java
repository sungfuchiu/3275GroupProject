package com.example.techassist.Repositories;

import com.example.techassist.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository  extends JpaRepository<Client,Long> {
    Optional<Client> findById(Long id);
}
