package com.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @ManyToMany(mappedBy = "courses")
    private List<User> students = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Exam> exams = new ArrayList<>();


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

    
}
