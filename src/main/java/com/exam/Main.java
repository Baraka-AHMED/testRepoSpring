package com.exam;

import com.exam.model.*;
import com.exam.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    	/*
        try {
            // Création d'un professeur
            User teacher = new User();
            teacher.setActive(true);
            teacher.setFirstName("John");
            teacher.setLastName("Doe");
            teacher.setEmail("john.doe@example.com");
            teacher.setPassword("securePass");
            teacher.setRole("TEACHER");
            teacher.setUsername("johndoe");
            userService.addUser(teacher);

            // Création d'un cours
            Course course = new Course();
            course.setTitle("Mathematics");
            courseService.addCourse(course);

            // Création d'un examen associé au cours et au professeur
            Exam exam = new Exam();
            exam.setExamTitle("Algebra Test");
            exam.setCourse(course);
            exam.setTeacher(teacher);
            examService.addExam(exam);

            // Création d'un étudiant
            User student = new User();
            student.setActive(true);
            student.setFirstName("Alice");
            student.setLastName("Smith");
            student.setEmail("alice.smith@example.com");
            student.setPassword("studentPass");
            student.setRole("STUDENT");
            student.setUsername("alicesmith");
            student.setCourses(List.of(course));
            student.setExams(List.of(exam));
            userService.addUser(student);

            // Création d'une question pour l'examen
            Question question = new Question();
            question.setCategory("Algebra");
            question.setDifficultyLevel("Medium");
            question.setOption1("2");
            question.setOption2("4");
            question.setOption3("6");
            question.setOption4("8");
            question.setRightAnswer("4");
            question.setQuestionTitle("What is 2+2?");
            question.setExam(exam);
            questionService.addQuestion(question);

            // Création d'un quiz
            Quiz quiz = new Quiz();
            quiz.setTitle("Math Quiz");
            quiz.setQuestions(List.of(question));
            quizService.addQuiz(quiz);

            // Affichage des résultats
            System.out.println("\n======= Données enregistrées =======");
            System.out.println("Enseignant: " + teacher.getFirstName() + " " + teacher.getLastName());
            System.out.println("Cours: " + course.getTitle());
            System.out.println("Examen: " + exam.getExamTitle());
            System.out.println("Étudiant: " + student.getFirstName() + " " + student.getLastName());
            System.out.println("Question: " + question.getQuestionTitle() + " | Réponse: " + question.getRightAnswer());
            System.out.println("Quiz: " + quiz.getTitle());
            System.out.println("==================================\n");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution de l'application: " + e.getMessage());
            e.printStackTrace();
        }
        */
    }
}