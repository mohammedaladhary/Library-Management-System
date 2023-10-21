package com.sda.ironhack.LibraryManagementSystem.Repositories;

import com.sda.ironhack.LibraryManagementSystem.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Define custom query methods if necessary
    Student findByUsn(String usn);
}
