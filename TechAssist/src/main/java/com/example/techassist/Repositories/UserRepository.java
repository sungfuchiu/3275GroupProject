package com.example.techassist.Repositories;

import com.example.techassist.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    //For searching records
    //List<User> findTechnicianById (long kw);
    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);
}

