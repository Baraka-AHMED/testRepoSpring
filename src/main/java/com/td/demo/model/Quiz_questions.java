package com.td.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz_questions")
@IdClass(Quiz_questions_id.class)
public class Quiz_questions {
	
	@Id
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Quiz quiz;

	@Id
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Question question;

}
