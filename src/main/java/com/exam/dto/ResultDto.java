package com.exam.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {

    @NotNull(message = "La note est obligatoire")
    @DecimalMin(value = "0.0", message = "La note doit être ≥ 0")
    @DecimalMax(value = "20.0", message = "La note doit être ≤ 20")
    private Double score;

    @NotNull
    private Long studentId;

    @NotNull
    private Long examId;

    public ResultDto(Double score, Long studentId, Long examId) {
        this.score = score;
        this.studentId = studentId;
        this.examId = examId;
    }
}
