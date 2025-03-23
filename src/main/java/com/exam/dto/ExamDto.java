package com.exam.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ExamDto {

	private Long examId;
	
    @NotBlank(message = "Le titre de l'examen est obligatoire.")
    private String examTitle;

    @NotNull(message = "La date de l'examen est obligatoire.")
    @Future(message = "La date de l'examen doit être antérieure à aujourd'hui.")
    private LocalDate examDate;

    @NotNull(message = "L'ID du cours est obligatoire.")
    private Long courseId;

}
