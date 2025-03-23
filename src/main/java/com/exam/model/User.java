package com.exam.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private Boolean active;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;



    @Column(nullable = false, unique = true)
    private String username;

    @ManyToMany
    @JoinTable(
            name = "course_students",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnore
    private List<Course> courses;

    @ManyToMany
    @JoinTable(
            name = "exam_students",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id")
    )
    
    @JsonIgnore
    private List<Exam> exams;
    
    
 // Constructeur avec 'active' par défaut à true
    public User() {
        this.active = true; 
    }
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role));
    };
    

    public void enrollInCourse(Course course) {
        if (!this.courses.contains(course)) {
            this.courses.add(course);
            course.getStudents().add(this);
        }
    }



    /*
     * Pas besoin d'implémenter les getters et setters quand on utilise les annotations @Getter et @Setter
     * Ces annotations les génèrent automatiquement sans qu'ils soient visibles dans le code
     * Sauf s'il y a une particularité dans le setter ou le getter
     */

}

