package com.sda.ironhack.LibraryManagementSystem.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // You can use a database-generated ID

    private String authorName;
    private String authorEmail;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    @OneToOne
    private Book authorBook;

    public Author(String authorName, String authorEmail, Book authorBook) {
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.authorBook = authorBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(authorName, author.authorName) && Objects.equals(authorEmail, author.authorEmail) && Objects.equals(authorBook, author.authorBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorName, authorEmail, authorBook);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", authorBook=" + authorBook +
                '}';
    }
}