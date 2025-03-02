package com.td.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam_students")
@IdClass(Exam_students_id.class)
public class Exam_students {

	@Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users student;

	@Id
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Exam exam;

}
