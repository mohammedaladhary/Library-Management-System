package com.sda.ironhack.LibraryManagementSystem.Repositories;

import com.sda.ironhack.LibraryManagementSystem.Entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByAuthorName(String authorName);
    // You can add custom query methods here if needed
}