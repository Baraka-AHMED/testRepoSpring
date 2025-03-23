package com.exam.controller.webController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.exam.model.Course;
import com.exam.model.Exam;
import com.exam.model.ExamStatus;
import com.exam.model.User;
import com.exam.service.CourseService;
import com.exam.service.ExamService;
import com.exam.service.UserService;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private CourseService courseService;  // Service pour récupérer les cours
    
    @Autowired
    private ExamService examService;      // Service pour récupérer les examens associés aux cours
    
    @Autowired
    private UserService userService;      // Service pour récupérer les informations de l'étudiant

    @GetMapping("/dashboard")
    public String showStudentDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User student = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Course> enrolledCourses = student.getCourses();

        Map<Long, List<Exam>> courseExams = new HashMap<>();
        for (Course course : enrolledCourses) {
            List<Exam> exams = examService.getExamsByCourseId(course.getId());
            
            List<Exam> publishedExams = exams.stream()
                    .filter(exam -> exam.getExamStatus() == ExamStatus.PUBLISHED)  // Filtrer les examens PUBLISHED
                    .collect(Collectors.toList());

            courseExams.put(course.getId(), publishedExams);
        }
        
        List<Course> allCourses = courseService.getAllCourses();
        List<Course> availableCourses = new ArrayList<Course>();
        for (Course course : allCourses) {
        	if (enrolledCourses.contains(course)) {
        		continue;
        	}
        	availableCourses.add(course);
        }

        model.addAttribute("student", student);
        model.addAttribute("enrolledCourses", enrolledCourses);
        model.addAttribute("courseExams", courseExams);
        model.addAttribute("availableCourses", availableCourses); 

        return "student_dashboard"; 
    }


    // Inscrire l'étudiant à un cours
    @PostMapping("/enroll")
    public String enrollToCourse(@RequestParam("courseId") Long courseId, @AuthenticationPrincipal UserDetails userDetails) {
        // Récupérer l'étudiant connecté
        User student = userService.findByUsername(userDetails.getUsername())
        		.orElseThrow(() -> new RuntimeException("Student not found"));

        // Inscrire l'étudiant au cours
        Course course = courseService.getCourseById(courseId)
        		.orElseThrow(() -> new RuntimeException("Course not found"));
        
        userService.enrollStudentToCourse(student, course);

        return "redirect:/student/dashboard";  
    }
    
    @GetMapping("/exam/{id}")
    public String viewExam1(@PathVariable Long id, Model model) {
        Exam exam = examService.getExamById(id)
        		.orElseThrow(() -> new RuntimeException("Exam not found"));

        if (exam != null && exam.getExamStatus() == ExamStatus.PUBLISHED) {
            model.addAttribute("exam", exam);
            return "exam-view";  
        } else {
            model.addAttribute("errorMessage", "Cet examen n'est pas encore publié.");
            return "error-page";  
        }
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Récupérer l'étudiant connecté
        User student = userService.findByUsername(userDetails.getUsername())
        		.orElseThrow(() -> new RuntimeException("Student not found"));

        model.addAttribute("student", student);

        return "edit_profile";  
    }

    // Enregistrer les modifications du profil
    @PostMapping("/edit-profile")
    public String updateProfile(@ModelAttribute User student, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        // Récupérer l'étudiant connecté
        User currentUser = userService.findByUsername(userDetails.getUsername())
        		.orElseThrow(() -> new RuntimeException("Student not found"));
        
        // Mettre à jour les informations de l'étudiant
        currentUser.setFirstName(student.getFirstName());
        currentUser.setLastName(student.getLastName());
        currentUser.setEmail(student.getEmail());   

        // Sauvegarder les modifications
        userService.updateUser(currentUser);
        
        // Ajouter un message de succès
        redirectAttributes.addFlashAttribute("message", "Informations mises à jour avec succès !");
        
        return "redirect:/student/edit-profile";  // Retourner à la page de modification du profil
    }
}
