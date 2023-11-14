package com.example.studentpractice.repositories;

import com.example.studentpractice.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findStudentById(long kw);
}
