package com.exam;

import com.exam.model.*;
import com.exam.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private ExamService examService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
