package com.exam.controller.webController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.exam.model.User;
import com.exam.service.CourseService;
import com.exam.service.UserService;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private CourseService courseService;  // Service pour récupérer les cours
    /*
    @Autowired
    private ExamService examService;      // Service pour récupérer les examens
    @Autowired
    private UserService userService; // Service pour récupérer les informations de l'étudiant
    
    

    @GetMapping("/dashboard")
    public String showStudentDashboard(Model model, Authentication authentication) {
        // Récupérer l'étudiant connecté
        User student = userService.findByUsername(authentication.getName())
        		.orElseThrow(()-> new RuntimeException("Student not found"));

        // Récupérer les cours auxquels l'étudiant est inscrit
        List<Course> enrolledCourses = student.getCourses();

        // Récupérer les examens pour chaque cours
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

        // Ajouter à la vue
        model.addAttribute("student", student);
        model.addAttribute("enrolledCourses", enrolledCourses);
        model.addAttribute("courseExams", courseExams);
        model.addAttribute("availableCourses", availableCourses); 

        return "student_dashboard";  // Nom de la vue Thymeleaf
    }

    
    @PostMapping("/enroll")
    public String enrollToCourse(@RequestParam("courseId") Long courseId, Authentication authentication) {
        // Récupérer l'étudiant
    	User student = userService.findByUsername(authentication.getName())
        		.orElseThrow(()-> new RuntimeException("Student not found"));

        // Inscrire l'étudiant au cours
        Course course = courseService.getCourseById(courseId)
        		.orElseThrow(()-> new RuntimeException("Course not found"));
        
        userService.enrollStudentToCourse(student, course);

        return "redirect:/student/dashboard";  // Retour au dashboard après inscription
    }
    
    
    @GetMapping("/exam/view/{id}")
    public String viewExam(@PathVariable Long id, Model model) {

    	Exam exam = examService.findExamById(id);

        model.addAttribute("exam", exam);

        return "view_exam";  
    }
    
    
    @GetMapping("/edit-profile")
    public String editProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {

    	User student = userService.findByUsername(userDetails.getUsername())
        		.orElseThrow(()-> new RuntimeException("Student not found"));

        model.addAttribute("student", student);

        return "edit_profile";  
    }
    
    
    @PostMapping("/edit-profile")
    public String updateProfile(@ModelAttribute User student, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
    	
    	User currentUser = userService.findByUsername(userDetails.getUsername())
        		.orElseThrow(()-> new RuntimeException("Student not found"));
    	
    	currentUser.setFirstName(student.getFirstName());
        currentUser.setLastName(student.getLastName());
        currentUser.setEmail(student.getEmail());   	

        userService.updateUser(currentUser);
        
        redirectAttributes.addFlashAttribute("message", "Informations mises à jour avec succès !");
        
        return "redirect:/student/edit-profile";
    }

*/
    
}
