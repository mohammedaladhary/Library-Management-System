package com.sda.ironhack.LibraryManagementSystem;

import com.sda.ironhack.LibraryManagementSystem.Entities.Author;
import com.sda.ironhack.LibraryManagementSystem.Entities.Book;
import com.sda.ironhack.LibraryManagementSystem.Entities.Issue;
import com.sda.ironhack.LibraryManagementSystem.Entities.Student;
import com.sda.ironhack.LibraryManagementSystem.Repositories.AuthorRepository;
import com.sda.ironhack.LibraryManagementSystem.Repositories.BookRepository;
import com.sda.ironhack.LibraryManagementSystem.Repositories.IssueRepository;
import com.sda.ironhack.LibraryManagementSystem.Repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryManagementSystemApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private LibraryServices libraryServices;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private IssueRepository issueRepository;

	private Author author1;
	private Author author2;
	private Book book1;
	private Book book2;
	private Student student1;
	private Student student2;
	private Issue issue1;
	private Issue issue2;
	@Test
	void contextLoads() {
	}

	@BeforeEach
	void setUp() {
		// Create and save test authors
		author1 = new Author("Test Author 1", "test.author1@example.com", null);
		author2 = new Author("Test Author 2", "test.author2@example.com", null);

		authorRepository.save(author1);
		authorRepository.save(author2);

		// Create and save test books associated with authors
		book1 = new Book("ISBN-111", "Book 1", "Category 1", 3);
		book2 = new Book("ISBN-222", "Book 2", "Category 2", 5);

		// Associate books with authors
		book1.setAuthor(author1);
		book2.setAuthor(author2);

		bookRepository.save(book1);
		bookRepository.save(book2);

		// Create and save test students
		student1 = new Student("USN-001", "John Doe");
		student2 = new Student("USN-002", "Jane Smith");

		studentRepository.save(student1);
		studentRepository.save(student2);

		// Create and save test issued books associated with students and books
		issue1 = new Issue(5L,"2023-01-01", "2023-02-01", student1, book1);
		issue2 = new Issue(6L,"2023-02-01", "2023-03-01", student2, book2);

		issueRepository.save(issue1);
		issueRepository.save(issue2);
	}
//	@AfterEach
//	void tearDown() {
//		bookRepository.deleteById(1L);
//	}

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

	@Test
	void testSearchBookByTitle() {
		Book book = (Book) bookRepository.findByTitle("Book 1");
		assertEquals("Book 1",book.getTitle());
	}


	@Test
	void testSearchBookByCategory() throws Exception {
		// Perform a GET request to search books by category
		mockMvc.perform(MockMvcRequestBuilders.get("/searchBookByCategory?category=Category 1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Category 1"));
	}

	@Test
	void testSearchBookByAuthor() throws Exception {
		// Perform a GET request to search books by author
		mockMvc.perform(MockMvcRequestBuilders.get("/searchBookByAuthor?author=Test Author 1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Test Author 1"));
	}

	@Test
	void testListAllBooks() throws Exception {
		// Perform a GET request to list all books
		mockMvc.perform(MockMvcRequestBuilders.get("/listAllBooks"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Book 1"))
				.andExpect(MockMvcResultMatchers.content().string("Book 2"))
				.andExpect(MockMvcResultMatchers.content().string("Test Author 1"))
				.andExpect(MockMvcResultMatchers.content().string("Test Author 2"));
	}

	@Test
	public void testIssueBookToStudent() {
		// Implement the logic to issue a book to a student
		libraryServices.handleIssueBookToStudent(issue1.getId(), student1.getUsn());

		// Verify that the book was successfully issued to the student
		Issue issuedBook = issueRepository.findById(issue1.getId()).orElse(null);
		assertNotNull(issuedBook);
		assertEquals(student1, issuedBook.getIssueStudent());
		assertEquals(student1, issuedBook.getIssueBook());
	}

	@Test
	public void testListBooksByUsn() {
		// Implement the logic to list books by USN
		List<Book> booksByUsn = libraryServices.handleListBooksByUsn(student1.getUsn());
		// Verify that the student's issued book is in the list
		assertTrue(booksByUsn.contains(book1));
	}
}