package com.sda.ironhack.LibraryManagementSystem;

import com.sda.ironhack.LibraryManagementSystem.Entities.Author;
import com.sda.ironhack.LibraryManagementSystem.Entities.Book;
import com.sda.ironhack.LibraryManagementSystem.Entities.Issue;
import com.sda.ironhack.LibraryManagementSystem.Entities.Student;
import com.sda.ironhack.LibraryManagementSystem.Repositories.AuthorRepository;
import com.sda.ironhack.LibraryManagementSystem.Repositories.BookRepository;
import com.sda.ironhack.LibraryManagementSystem.Repositories.IssueRepository;
import com.sda.ironhack.LibraryManagementSystem.Repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LibraryManagementSystemApplication implements CommandLineRunner {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private IssueRepository issueRepository;

	@Autowired
	private LibraryServices libraryServices;

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("\nWelcome to the Library Management System");

		// Implement a menu to call various library services
		while (true) {
			System.out.println("\nSelect from 1 - 7 or 0 to exit: ");
			System.out.println("\n1. Add a Book and Author");
			System.out.println("2. Search book by title");
			System.out.println("3. Search book by category");
			System.out.println("4. Search book by Author");
			System.out.println("5. List all books along with author");
			System.out.println("6. Issue book to student");
			System.out.println("7. List books by usn");
			System.out.println("8. Add Student");
			System.out.println("0. Exit");

			// Read user choice
			int choice = getUserChoice();

			switch (choice) {
				case 1:
					handleAddBookAndAuthor();
					break;
				case 2:
					handleSearchBookByTitle();
					break;
				case 3:
					handleSearchBookByCategory();
					break;
				case 4:
					handleSearchBookByAuthor();
					break;
				case 5:
					handleListAllBooks();
					break;
				case 6:
					handleIssueBookToStudent();
					break;
				case 7:
					handleListBooksByUsn();
					break;
				case 8:
					addStudent();
					break;
				case 0:
					System.out.println("Exiting the application.");
					System.exit(0);
				default:
					System.out.println("Invalid choice. Please select a valid option.");
			}
		}
	}

	private int getUserChoice() {
		Scanner scanner = new Scanner(System.in);
		int choice;

		try {
			System.out.print("\nEnter your choice: ");
			choice = scanner.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid input. Please enter a valid choice.");
			return getUserChoice(); // Recursively call the method for a valid choice
		}

		return choice;
	}

	private void handleAddBookAndAuthor() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter ISBN: ");
		String isbn = scanner.next();
		System.out.print("Enter title: ");
		String title = scanner.next();
		System.out.print("Enter category: ");
		String category = scanner.next();
		System.out.print("Enter Author name: ");
		String authorName = scanner.next();
		System.out.print("Enter Author email: ");
		String authorEmail = scanner.next();
		System.out.print("Enter number of books: ");
		int quantity = scanner.nextInt();

		libraryServices.addBookAndAuthor(isbn, title, category, authorName, authorEmail, quantity);
	}
	private void handleSearchBookByTitle() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the title to search for: ");
		String title = scanner.nextLine();

		// Search books by title using bookRepository
		List<Book> books = bookRepository.findByTitle(title);

		if (books.isEmpty()) {
			System.out.println("No books found with the given title.");
		} else {
			System.out.println("\nBooks with the title '" + title + "':");
			System.out.println("----------------------------------------------------------------------------------------");
			System.out.printf("%-20s %-20s %-15s %-15s%n", "Book ISBN", "Book Title", "Category", "No of Books");
			System.out.println("----------------------------------------------------------------------------------------");
			for (Book book : books) {
				System.out.printf("%-20s %-20s %-15s %-15s%n", book.getIsbn(), book.getTitle(), book.getCategory(), book.getQuantity());
			}
			System.out.println("----------------------------------------------------------------------------------------");
		}
	}



	private void handleSearchBookByCategory() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the category to search for: ");
		String category = scanner.nextLine();

		// Search books by category using bookRepository
		List<Book> books = bookRepository.findByCategory(category);

		if (books.isEmpty()) {
			System.out.println("No books found in the given category.");
		} else {
			System.out.println("\nBooks in the category '" + category + "':");
			System.out.println("----------------------------------------------------------------------------------------");
			System.out.printf("%-20s %-20s %-15s %-15s%n", "Book ISBN", "Book Title", "Category", "No of Books");
			System.out.println("----------------------------------------------------------------------------------------");
			for (Book book : books) {
				System.out.printf("%-20s %-20s %-15s %-15s%n", book.getIsbn(), book.getTitle(), book.getCategory(), book.getQuantity());
			}
			System.out.println("----------------------------------------------------------------------------------------");
		}
	}


	private void handleSearchBookByAuthor() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the author's name to search for: ");
		String authorName = scanner.nextLine();

		// Search books by author's name using authorRepository
		List<Author> authors = authorRepository.findByAuthorName(authorName);

		if (authors.isEmpty()) {
			System.out.println("No books found by the given author.");
		} else {
			System.out.println("\nBooks by author '" + authorName + "':");
			System.out.println("----------------------------------------------------------------------------------------");
			System.out.printf("%-20s %-20s %-15s %-15s%n", "Book ISBN", "Book Title", "Category", "No of Books");
			System.out.println("----------------------------------------------------------------------------------------");
			for (Author author : authors) {
				Book book = author.getAuthorBook();
				System.out.printf("%-20s %-20s %-15s %-15s%n", book.getIsbn(), book.getTitle(), book.getCategory(), book.getQuantity());
			}
			System.out.println("----------------------------------------------------------------------------------------");
		}
	}


	private void handleListAllBooks() {
		// Retrieve all books along with their authors
		List<Book> books = bookRepository.findAll();

		if (books.isEmpty()) {
			System.out.println("No books found in the library.");
		} else {
			System.out.println("\nList of all books along with their authors:");
			System.out.println("----------------------------------------------------------------------------------------------------------");
			System.out.printf("%-20s %-20s %-15s %-15s %-20s %-30s%n", "Book ISBN", "Book Title", "Category", "No of Books", "Author name", "Author email");
			System.out.println("----------------------------------------------------------------------------------------------------------");
			for (Book book : books) {
				Author author = book.getAuthor();
				System.out.printf("%-20s %-20s %-15s %-15s %-20s %-30s%n",
						book.getIsbn(), book.getTitle(), book.getCategory(), book.getQuantity(),
						author != null ? author.getAuthorName() : "N/A",
						author != null ? author.getAuthorEmail() : "N/A");
			}
		}
	}

	public void addStudent() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the student's USN: ");
		String usn = scanner.next();

		// Consume the newline character
		scanner.nextLine();

		System.out.print("Enter the student's name: ");
		String name = scanner.nextLine();

		Student student = new Student();
		student.setUsn(usn);
		student.setStudentName(name);

		studentRepository.save(student);

		System.out.println("Student added successfully.");
	}

	private void handleIssueBookToStudent() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the student's USN: ");
		String usn = scanner.next();
		System.out.print("Enter the book's ISBN: ");
		String isbn = scanner.next();

		// Implement the logic to issue the book to the student
		// 1. Check if the student (by USN) and book (by ISBN) exist in the database.
		Student student = studentRepository.findByUsn(usn);
		Book book = bookRepository.findByIsbn(isbn);

		if (student == null || book == null) {
			System.out.println("Student or book not found. Please check the USN and ISBN.");
			return;
		}

		// 2. Check if the book is available (quantity > 0).
		if (book.getQuantity() <= 0) {
			System.out.println("Book is not available for issue.");
			return;
		}

		// 3. Create an Issue record to track the book issuance.
		Issue issue = new Issue();
		issue.setIssueStudent(student);
		issue.setIssueBook(book);

		// Set issue date and return date
		LocalDate issueDate = LocalDate.now();
		LocalDate returnDate = issueDate.plusDays(30); // You can change the return date calculation as needed

		issue.setIssueDate(String.valueOf(issueDate));
		issue.setReturnDate(String.valueOf(returnDate));

		// 4. Update the book quantity and save the Issue record to the database.
		book.setQuantity(book.getQuantity() - 1);
		bookRepository.save(book);
		issueRepository.save(issue);

		System.out.println("Book issued successfully to " + student.getStudentName() + ". Return date: " + returnDate);
	}

//	private void handleIssueBookToStudent() {
//		Scanner scanner = new Scanner(System.in);
//
//		System.out.print("Enter the student's USN: ");
//		String usn = scanner.next();
//		System.out.print("Enter the book's ISBN: ");
//		String isbn = scanner.next();
//
//		// Implement the logic to issue the book to the student
//		// 1. Check if the student (by USN) and book (by ISBN) exist in the database.
//		Student student = studentRepository.findByUsn(usn);
//		Book book = bookRepository.findByIsbn(isbn);
//
//		if (student == null || book == null) {
//			System.out.println("Student or book not found. Please check the USN and ISBN.");
//			return;
//		}
//
//		// 2. Check if the book is available (quantity > 0).
//		if (book.getQuantity() <= 0) {
//			System.out.println("Book is not available for issue.");
//			return;
//		}
//
//		// 3. Create an Issue record to track the book issuance.
//		Issue issue = new Issue();
//		issue.setIssueStudent(student);
//		issue.setIssueBook(book);
//
//		// 4. Update the book quantity and save the Issue record to the database.
//		book.setQuantity(book.getQuantity() - 1);
//		bookRepository.save(book);
//		issueRepository.save(issue);
//
//		// 5. Generate a return date
//		Date returnDate = generateReturnDate();
//
//		System.out.println("Book issued. Return date: " + returnDate);
//	}

	private Date generateReturnDate() {
		// Implement logic to calculate the return date (e.g., add a fixed duration to the current date)
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 30); // For example, 30 days loan period

		return calendar.getTime();
	}

	private void handleListBooksByUsn() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the student's USN: ");
		String usn = scanner.next();

		// Implement the logic to list books issued to a student by USN
		Student student = studentRepository.findByUsn(usn);

		if (student == null) {
			System.out.println("Student not found. Please check the USN.");
			return;
		}

		List<Issue> issues = issueRepository.findByIssueStudent(student);

		if (issues.isEmpty()) {
			System.out.println("No books issued to this student.");
		} else {
			System.out.println("Books issued to " + student.getStudentName() + " (USN: " + student.getUsn() + "):");
			for (Issue issue : issues) {
				System.out.println(issue.getIssueBook().getTitle());
			}
		}
	}


}
