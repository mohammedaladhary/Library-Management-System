package com.sda.ironhack.LibraryManagementSystem.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // You can use a database-generated ID

    private String issueDate;
    private String returnDate;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student issueStudent;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book issueBook;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(id, issue.id) && Objects.equals(issueDate, issue.issueDate) && Objects.equals(returnDate, issue.returnDate) && Objects.equals(issueStudent, issue.issueStudent) && Objects.equals(issueBook, issue.issueBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, issueDate, returnDate, issueStudent, issueBook);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", issueDate='" + issueDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", issueStudent=" + issueStudent +
                ", issueBook=" + issueBook +
                '}';
    }
}
