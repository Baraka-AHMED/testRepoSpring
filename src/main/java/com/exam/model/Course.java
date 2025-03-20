package com.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Exam> exams = new ArrayList<>();

    @ManyToMany(mappedBy = "courses")
    private List<User> students = new ArrayList<>();


    // Ajout d’un étudiant à la liste des étudiants du cours
    public void addStudent(User student) {
        if (!this.students.contains(student)) {
            this.students.add(student);
            student.getCourses().add(this);
        }
    }

    // Ajout d’un examen à la liste des examens du cours
    public void addExam(Exam exam) {
        if (!this.exams.contains(exam)) {
            this.exams.add(exam);
            exam.setCourse(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }
}
