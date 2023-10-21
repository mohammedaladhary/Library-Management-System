package com.sda.ironhack.LibraryManagementSystem.Repositories;

import com.sda.ironhack.LibraryManagementSystem.Entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // You can add custom query methods here if needed
}