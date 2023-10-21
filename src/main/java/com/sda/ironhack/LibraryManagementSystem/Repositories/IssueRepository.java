package com.sda.ironhack.LibraryManagementSystem.Repositories;

import com.sda.ironhack.LibraryManagementSystem.Entities.Issue;
import com.sda.ironhack.LibraryManagementSystem.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    // Define custom query methods if necessary
    List<Issue> findByIssueStudent(Student student);

    List<Issue> findByIssueStudentUsn(String usn);
}
