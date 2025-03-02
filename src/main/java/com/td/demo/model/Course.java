package com.td.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	@Getter @Setter
    private String title;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Course_students> students;
	
	public Course(String title) {
		this.title = title;
		this.students = new ArrayList<Course_students>();
	}
	
	public Course() {
		// Ce constructeur sans argument a été créé car Lombok n'a pas pu correctement créé le constructeur avec @No ArgsConstructor
		// HIbernate a besoin de ce constructeur par défaut pour créer des sinstances
	}
	
    // Getter pour title a été écrit manuellement car Lombok ne l'a pas pu correctement créé automatiquement à l'aide des annotations
    public String getTitle() {
        return title;
    }

    // Setter pour title a été écrit manuellement car Lombok ne l'a pas pu correctement créé automatiquement à l'aide des annotations
    public void setTitle(String title) {
        this.title = title;
    }
	
}
