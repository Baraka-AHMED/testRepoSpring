package com.exam.controller.webController;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.context.SecurityContextHolder;

import com.exam.model.Course;
import com.exam.model.Exam;
import com.exam.model.Question;
import com.exam.model.Quiz;
import com.exam.model.User;
import com.exam.service.CourseService;
import com.exam.service.ExamService;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import com.exam.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showTeacherDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    	
    	User user = userService.findByUsername(userDetails.getUsername())
    			.orElseThrow(() -> new BadCredentialsException("User not found "));;
    			
		model.addAttribute("username", userDetails.getUsername());
    	
        List<Course> teacherCourses = courseService.findCoursesByTeacherId(user.getUserId());
        model.addAttribute("teacherCourses", teacherCourses);

        List<Exam> exams = examService.findExamsByTeacherId(user.getUserId());
        model.addAttribute("exams", exams);
        
        List<Course> allCourses = courseService.getAllCourses();
    	model.addAttribute("allCourses",allCourses);
        return "teacher_dashboard";
    }
    
    
    @GetMapping("/manage-exam/{courseId}/{examId}")
    public String manageExams(@PathVariable Long courseId, @PathVariable Long examId, Model model, HttpSession session) {
        // Charger l'examen spécifique par son ID
        Exam exam = examService.findExamById(examId);

        // Vérifier si l'examen existe et appartient bien au cours donné
        if (exam != null && exam.getCourse().getId().equals(courseId)) {
            model.addAttribute("exam", exam);
            model.addAttribute("courseId", courseId);
            session.setAttribute("courseId", courseId);
            session.setAttribute("examId", exam.getId());
        } else {
            // Si l'examen n'existe pas ou n'appartient pas au cours, rediriger vers une autre page (par exemple, une erreur ou la liste des cours)
            return "redirect:/teacher/dashboard";
        }
        return "manage_exam"; // Page pour afficher les détails d'un examen spécifique
    }


 // Créer un nouvel examen pour un cours donné
    @PostMapping("/create-exam")
    public String createExam(@RequestParam String examTitle, @RequestParam Long courseId) {
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Cours non trouvé"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User teacher = userService.findByUsername(auth.getName())
        		.orElseThrow(()-> new RuntimeException("Utiliisateur inconnu"));

        Exam exam = new Exam();
        exam.setExamTitle(examTitle);
        exam.setCourse(course);
        exam.setTeacher(teacher);

        examService.addExam(exam);
        return "redirect:/teacher/dashboard";
    }
    
    @GetMapping("/edit-exam/{examId}")
    public String editExam(@PathVariable Long examId, Model model) {
        // Charger les détails de l'examen pour modification
        Exam exam = examService.findExamById(examId);
        model.addAttribute("exam", exam);
        return "teacher/edit-exam";
    }

    @PostMapping("/delete-exam/{examId}")
    public String deleteExam(@PathVariable Long examId) {
        // Supprimer l'examen
        examService.deleteExamById(examId);
        return "redirect:/teacher/dashboard"; // Rediriger vers le dashboard après suppression
    }
    
 // Ajouter une question à un examen
    @GetMapping("/add-question/{examId}")
    public String addQuestionToExam(@PathVariable("examId") Long examId, Model model, HttpSession session) {
        model.addAttribute("examId", examId);
        model.addAttribute("question", new Question());
        model.addAttribute("isEdit", false);
        session.setAttribute("examId", examId);
        return "question_form";
    }
    
    
    @GetMapping("/edit-question/{questionId}")
    public String editQuestionForm(@PathVariable Long questionId, Model model, HttpSession session) {
        Question question = questionService.getQuestionById(questionId);      
        model.addAttribute("question", question);
        model.addAttribute("isEdit", true);
        session.setAttribute("examId", question.getExam().getId());
        return "question_form"; // Redirige vers la vue du formulaire de modification
    }
    
    @PostMapping("/save-question")
    public String saveQuestion(@ModelAttribute Question question, HttpSession session) {
    	
    	Long examId = (Long) session.getAttribute("examId");
    	
    	Exam exam = examService.getExamById(examId)
    	        .orElseThrow(() -> new IllegalArgumentException("Invalid Exam ID: " + examId));

    	System.out.println("l'examen est bien récupéré : "+ exam.getExamTitle());
    	
    	question.setExam(exam);
        questionService.saveQuestion(question);
        
        
        
        return "redirect:/teacher/manage-exam/" + session.getAttribute("courseId") + "/" + examId;
    }


    @PostMapping("/delete-question/{questionId}")
    public String deleteQuestion(@PathVariable Long questionId, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            questionService.deleteQuestionById(questionId);
            redirectAttributes.addFlashAttribute("successMessage", "Question supprimée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression de la question.");
        }
                
        return "redirect:/teacher/manage-exam/" + session.getAttribute("courseId") + "/" + session.getAttribute("examId"); // 
    }

    

    @GetMapping("/manage-questions/{examId}")
    public String manageQuestions(@PathVariable Long examId, Model model) {
        // Charger les questions associées à l'examen spécifique
        List<Question> questions = questionService.findQuestionsByExamId(examId);
        model.addAttribute("questions", questions);

        return "teacher/manage-questions";
    }
    
    
    @GetMapping("/course-students/{courseId}")
    public String listStudentsByCourse(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Cours non trouvé"));

        List<User> students = course.getStudents(); // Récupère les étudiants inscrits

        model.addAttribute("course", course);
        model.addAttribute("students", students);

        return "course_students"; // Vue qui affichera les étudiants
    }

    
    

}
