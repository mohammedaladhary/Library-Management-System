package com.sda.ironhack.LibraryManagementSystem;

import com.sda.ironhack.LibraryManagementSystem.Entities.Author;
import com.sda.ironhack.LibraryManagementSystem.Entities.Book;
import com.sda.ironhack.LibraryManagementSystem.Entities.Issue;
import com.sda.ironhack.LibraryManagementSystem.Entities.Student;
import com.sda.ironhack.LibraryManagementSystem.Repositories.AuthorRepository;
import com.sda.ironhack.LibraryManagementSystem.Repositories.BookRepository;
import com.sda.ironhack.LibraryManagementSystem.Repositories.IssueRepository;
import com.sda.ironhack.LibraryManagementSystem.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryServices {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private StudentRepository studentRepository;

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


    public List<Book> handleListBooksByUsn(String usn) {
        // Retrieve a list of books issued to the student with the given USN
        List<Issue> issuedBooks = issueRepository.findByIssueStudentUsn(usn);

        // Create a list of books issued to the student
        List<Book> booksIssuedToStudent = issuedBooks.stream()
                .map(Issue::getIssueBook)
                .collect(Collectors.toList());

        return booksIssuedToStudent;
    }

    public void handleIssueBookToStudent(Long id, String usn) {
        // Find the book by its ID (assuming you have a BookRepository)
        Book book = bookRepository.findById(id).orElse(null);

        // Find the student by their USN (assuming you have a StudentRepository)
        Student student = studentRepository.findByUsn(usn);

        if (book == null) {
            System.err.println("Book not found.");
        } else if (student == null) {
            System.err.println("Student not found.");
        } else {
            // Create an Issue entity to represent the book being issued
            Issue issue = new Issue();
            issue.setIssueDate("2023-10-21");  // Set the issue date as needed
            issue.setReturnDate("2023-11-21");  // Set the return date as needed
            issue.setIssueStudent(student);
            issue.setIssueBook(book);

            // Save the issue entity (assuming you have an IssueRepository)
            issueRepository.save(issue);

            System.out.println("Book issued to student successfully.");
        }
    }
}
