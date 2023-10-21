package com.sda.ironhack.LibraryManagementSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

@SpringBootApplication
public class LibraryManagementSystemApplication implements CommandLineRunner {

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
			System.out.println("Select from 1 - 7 or 0 to exit: ");
			System.out.println("\n1. Add a Book and Author");
			System.out.println("2. Other options...");
			System.out.println("0. Exit");

			// Read user choice
			int choice = getUserChoice();

			switch (choice) {
				case 1:
					// Add a Book and Author
					handleAddBookAndAuthor();
					break;
				case 2:
					// Implement other menu options
					break;
				case 0:
					System.out.println("Exiting the application.");
					return;
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
}
