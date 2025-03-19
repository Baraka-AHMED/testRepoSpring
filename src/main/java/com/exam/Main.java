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
    	/*
        try {
            System.out.println("===== Initialisation des données =====");

            // Vérifier si un admin existe déjà, sinon en créer un par défaut
            User admin = userService.findByUsername("root").orElse(null);
            if (admin == null) {
                User rootAdmin = new User();
                rootAdmin.setActive(true);
                rootAdmin.setFirstName("Super");
                rootAdmin.setLastName("Admin");
                rootAdmin.setEmail("admin@example.com");
                rootAdmin.setPassword("root"); // Sera hashé
                rootAdmin.setRole(UserRole.ADMIN);
                rootAdmin.setUsername("root");
                userService.addUser(rootAdmin);
                System.out.println("⚡ Admin 'root' créé avec succès !");
            } else {
                System.out.println("✅ Un admin existe déjà, aucune création nécessaire.");
            }


            // **Création des professeurs**
            User teacher1 = createTeacher("Lundi", "Lemoine", "lundi.lemoine@example.com", "lundi123");
            User teacher2 = createTeacher("Marcel", "Dupont", "marcel.dupont@example.com", "marcel123");
            User teacher3 = createTeacher("Fatou", "Sow", "fatou.sow@example.com", "fatou123");

            // **Création des cours**
            Course course1 = createCourse("Mathématiques Avancées");
            Course course2 = createCourse("Physique Quantique");
            Course course3 = createCourse("Histoire Moderne");
            Course course4 = createCourse("Programmation en Java");

            // **Création des examens**
            Exam exam1 = createExam("Évaluation Algèbre", course1, teacher1);
            Exam exam2 = createExam("Test de Relativité", course2, teacher2);
            Exam exam3 = createExam("Examen Révolution Française", course3, teacher3);
            Exam exam4 = createExam("Examen Spring Boot", course4, teacher1);

            // **Création des étudiants**
            User student1 = createStudent("Kevin", "Diarra", "kevin.diarra@example.com", "kevin123", List.of(course1, course2));
            User student2 = createStudent("Sarah", "Bourgeois", "sarah.bourgeois@example.com", "sarah123", List.of(course3, course4));
            User student3 = createStudent("Omar", "Ba", "omar.ba@example.com", "omar123", List.of(course1, course4));

            // **Ajout des questions aux examens**
            addQuestionsToExam(exam1, Arrays.asList(
                    createQuestion("Algebra", "Combien font 5 + 7 ?", "10", "11", "12", "13", "12"),
                    createQuestion("Algebra", "Quel est le carré de 6 ?", "30", "36", "42", "48", "36")
            ));

            addQuestionsToExam(exam2, Arrays.asList(
                    createQuestion("Physique", "Qui a développé la théorie de la relativité ?", "Newton", "Einstein", "Galilée", "Tesla", "Einstein"),
                    createQuestion("Physique", "Quelle est la vitesse de la lumière ?", "300 000 km/s", "150 000 km/s", "1 000 km/s", "100 000 km/s", "300 000 km/s")
            ));

            addQuestionsToExam(exam3, Arrays.asList(
                    createQuestion("Histoire", "En quelle année a eu lieu la Révolution Française ?", "1789", "1804", "1765", "1830", "1789"),
                    createQuestion("Histoire", "Qui était le roi de France en 1789 ?", "Louis XIV", "Louis XVI", "Napoléon", "François Ier", "Louis XVI")
            ));

            addQuestionsToExam(exam4, Arrays.asList(
                    createQuestion("Programmation", "Quelle annotation est utilisée pour déclarer une entité en JPA ?", "@Entity", "@Table", "@Component", "@Service", "@Entity"),
                    createQuestion("Programmation", "Quelle est l’extension d’un fichier Java ?", ".js", ".java", ".py", ".html", ".java")
            ));

            System.out.println("===== Données enregistrées avec succès =====");

        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution de l'application: " + e.getMessage());
            e.printStackTrace();
        }*/
    }
    

    // **Méthodes utilitaires pour créer les entités**
    private User createTeacher(String firstName, String lastName, String email, String password) {
        User teacher = new User();
        teacher.setActive(true);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        teacher.setPassword(password);
        teacher.setRole(UserRole.TEACHER);
        teacher.setUsername(email.split("@")[0]);
        userService.addUser(teacher);
        return teacher;
    }

    private User createStudent(String firstName, String lastName, String email, String password, List<Course> courses) {
        User student = new User();
        student.setActive(true);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPassword(password);
        student.setRole(UserRole.STUDENT);
        student.setUsername(email.split("@")[0]);
        student.setCourses(courses);
        userService.addUser(student);
        return student;
    }

    private Course createCourse(String title) {
        Course course = new Course();
        course.setTitle(title);
        courseService.addCourse(course);
        return course;
    }

    private Exam createExam(String title, Course course, User teacher) {
        Exam exam = new Exam();
        exam.setExamTitle(title);
        exam.setCourse(course);
        exam.setTeacher(teacher);
        examService.addExam(exam);
        return exam;
    }

    private Question createQuestion(String category, String title, String opt1, String opt2, String opt3, String opt4, String correctAnswer) {
        Question question = new Question();
        question.setCategory(category);
        question.setDifficultyLevel("Medium");
        question.setQuestionTitle(title);
        question.setOption1(opt1);
        question.setOption2(opt2);
        question.setOption3(opt3);
        question.setOption4(opt4);
        question.setRightAnswer(correctAnswer);
        return question;
    }

    private void addQuestionsToExam(Exam exam, List<Question> questions) {
        for (Question question : questions) {
            question.setExam(exam);
            questionService.saveQuestion(question);
        }
    }
}
