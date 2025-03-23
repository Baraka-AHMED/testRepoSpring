package com.exam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuestionDto {
    private Long id;
    private String category;
    private String difficultyLevel;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String questionTitle;
    private String rightAnswer;
    private Long examId;

}