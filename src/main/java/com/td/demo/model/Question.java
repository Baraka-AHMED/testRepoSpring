package com.td.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
    private String  category;
	
	@Column(nullable = false)
    private String difficulty_level;
	
	@Column(nullable = false)
    private String option1;
	
	@Column(nullable = false)
    private String option2;
	
	@Column(nullable = false)
    private String option3;
	
	@Column(nullable = false)
    private String option4;
	
	@Column(nullable = false)
    private String question_title;
	
	@Column(nullable = false)
    private int right_answer;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Exam exam;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Quiz_questions> quizs;
	
}
