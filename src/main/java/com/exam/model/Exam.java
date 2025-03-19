package com.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String examTitle;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @JsonIgnore
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "exam_students",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<User> students = new ArrayList<>();

    public Exam() {}

    public Exam(String examTitle, Course course, User teacher) {
        this.examTitle = examTitle;
        this.course = course;
        this.teacher = teacher;
    }

    public void addStudent(User student) {
        if (!students.contains(student)) {
            students.add(student);
            student.getExams().add(this);
        }
    }

    // Retirer
    public void removeStudent(User student) {
        if (students.contains(student)) {
            students.remove(student);
            student.getExams().remove(this);
        }
    }

    public void setCourse(Course course) {
        this.course = course;
        if (!course.getExams().contains(this)) {
            course.getExams().add(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public Course getCourse() {
        return course;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }
}
