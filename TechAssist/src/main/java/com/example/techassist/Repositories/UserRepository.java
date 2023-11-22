package com.example.techassist.Repositories;

import com.example.techassist.Entities.ServiceField;
import com.example.techassist.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);
}

