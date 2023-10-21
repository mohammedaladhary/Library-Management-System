package com.sda.ironhack.LibraryManagementSystem;

import com.sda.ironhack.LibraryManagementSystem.Entities.Author;
import com.sda.ironhack.LibraryManagementSystem.Entities.Book;
import com.sda.ironhack.LibraryManagementSystem.Repositories.AuthorRepository;
import com.sda.ironhack.LibraryManagementSystem.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryServices {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public void addBookAndAuthor(String isbn, String title, String category, String authorName, String authorEmail, int quantity) {
        try {
            Book book = new Book(isbn, title, category, quantity);
            Author author = new Author(authorName, authorEmail, book);

            bookRepository.save(book);
            authorRepository.save(author);

            System.out.println("Book and author added successfully.");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
