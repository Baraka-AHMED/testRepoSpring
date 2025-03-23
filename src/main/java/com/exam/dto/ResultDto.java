package com.exam.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResultDto {
    @NotBlank(message = "La note est obligatoire")
    @DecimalMin(value = "0", message = "La note doit être supérieure ou égale à 0")
    @DecimalMax(value = "20", message = "La note doit être inférieure ou égale à 20")
    private Double score;

    private Long studentId;

    private Long examId;

    
}
