package com.td.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;
	
	@Column(nullable = false)
	private boolean active;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String first_name;
	
	@Column(nullable = false)
	private String last_name;
	
	@JsonIgnore
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String role;
	
	@Column(nullable = false)
	private String username;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Course_students> courses;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Exam_students> exams;
	
	
}
