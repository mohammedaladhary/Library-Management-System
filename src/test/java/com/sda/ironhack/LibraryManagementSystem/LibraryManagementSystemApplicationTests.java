package com.sda.ironhack.LibraryManagementSystem;

import com.sda.ironhack.LibraryManagementSystem.Entities.Author;
import com.sda.ironhack.LibraryManagementSystem.Entities.Book;
import com.sda.ironhack.LibraryManagementSystem.Repositories.AuthorRepository;
import com.sda.ironhack.LibraryManagementSystem.Repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LibraryManagementSystemApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@BeforeEach
	void setUp() {
		// Create and save test authors
		Author author1 = new Author("Test Author 1", "test.author1@example.com", null);
		Author author2 = new Author("Test Author 2", "test.author2@example.com", null);

		authorRepository.save(author1);
		authorRepository.save(author2);

		// Create and save test books associated with authors
		Book book1 = new Book("ISBN-111", "Book 1", "Category 1", 3);
		Book book2 = new Book("ISBN-222", "Book 2", "Category 2", 5);

		// Associate books with authors
		book1.setAuthor(author1);
		book2.setAuthor(author2);

		bookRepository.save(book1);
		bookRepository.save(book2);
	}

	@Test
	void testAddBookAndAuthor() {

		// Retrieve the book and author from the database
		Book savedBook = bookRepository.findByIsbn("978-3-16-148410-0");
		Author savedAuthor = (Author) authorRepository.findByAuthorName("Test Author");

		// Assert that the book and author were saved correctly
		assertEquals("ISBN-111", savedBook.getIsbn());
		assertEquals("Book 2", savedBook.getTitle());
		assertEquals("Test Author 2", savedAuthor.getAuthorName());
	}
}