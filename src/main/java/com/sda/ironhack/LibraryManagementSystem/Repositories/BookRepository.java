package com.sda.ironhack.LibraryManagementSystem.Repositories;

import com.sda.ironhack.LibraryManagementSystem.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);

    List<Book> findByCategory(String category);

    Book findByIsbn(String isbn);
}

