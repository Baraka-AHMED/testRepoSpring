package com.exam.controller.webController;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.context.SecurityContextHolder;

import com.exam.dto.ExamDto;
import com.exam.dto.QuestionDto;
import com.exam.dto.ResultDto;
import com.exam.model.Course;
import com.exam.model.Exam;
import com.exam.model.Question;
import com.exam.model.Quiz;
import com.exam.model.Result;
import com.exam.model.User;
import com.exam.service.CourseService;
import com.exam.service.ExamService;
import com.exam.service.QuestionService;
import com.exam.service.ResultService;
import com.exam.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private CourseService courseService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ExamService examService;
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private ResultService resultService;

    @GetMapping("/dashboard")
    public String showTeacherDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    	
    	User user = userService.findByUsername(userDetails.getUsername())
    			.orElseThrow(() -> new BadCredentialsException("User not found "));;
    			
		model.addAttribute("name", user.getFirstName());
    	
        List<Course> teacherCourses = courseService.findCoursesByTeacherId(user.getUserId());
        model.addAttribute("teacherCourses", teacherCourses);

        /*
        List<Exam> exams = examService.findExamsByTeacherId(user.getUserId());
        model.addAttribute("exams", exams);
        */
        
        List<Course> allCourses = courseService.getAllCourses();
    	model.addAttribute("allCourses",allCourses);
        return "teacher_dashboard";
    }
    
    
    @GetMapping("/create-exam/{courseId}")
    public String showCreateExamPage(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId)
        		.orElseThrow(() -> new RuntimeException("Course not found"));
        model.addAttribute("course", course);
        model.addAttribute("exam", new Exam());
        return "create_exam";
    }
    
    
    @PostMapping("/create-exam")
    public String createExam(@Valid @ModelAttribute ExamDto examDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	System.out.println("00");
            model.addAttribute("course", courseService.getCourseById(examDto.getCourseId()).orElse(null));
            return "create_exam";  // Retourne la même page avec les erreurs
        }

        System.out.println("01");
        
        Course course = courseService.getCourseById(examDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        System.out.println("02");

        // Vérification supplémentaire côté serveur
        if (examDto.getExamDate().isBefore(LocalDate.now()) || examDto.getExamDate().isEqual(LocalDate.now())) {
            model.addAttribute("error", "La date de l'examen doit être antérieure à aujourd'hui.");
            return "create_exam";
        }
        
        System.out.println("03");

        Exam exam = new Exam();
        exam.setExamTitle(examDto.getExamTitle());
        exam.setExamDate(examDto.getExamDate());
        exam.setCourse(course);
        examService.addExam(exam);

        System.out.println("04");
        
        return "redirect:/teacher/dashboard";  // Redirection après succès
    }

    
    // Afficher la liste des étudiants inscrits à un cours
    @GetMapping("/course-students/{courseId}")
    public String showCourseStudents(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        List<User> enrolledStudents = userService.getUsersByCourse(course);
        List<User> unenrolledStudents = userService.getUnenrolledStudents (course);
        unenrolledStudents.removeAll(enrolledStudents); // Exclure les étudiants déjà inscrits

        model.addAttribute("course", course);
        model.addAttribute("enrolledStudents", enrolledStudents);
        model.addAttribute("unenrolledStudents", unenrolledStudents);

        return "course_students";
    }
    
    
    @PostMapping("/subscribe-student")
    public String subscribeStudent(@RequestParam("courseId") Long courseId, 
                                   @RequestParam("studentId") Long studentId, 
                                   Model model) {
        try {
            courseService.enrollStudentInCourse(courseId, studentId);

            return "redirect:/teacher/course-students/" + courseId;  
        } catch (Exception e) {
            model.addAttribute("error", "Impossible d'inscrire l'étudiant.");
            return "error"; 
        }
    }
    
    @PostMapping("/unsubscribe-student")
    public String unsubscribeStudent(@RequestParam("courseId") Long courseId,
                                     @RequestParam("studentId") Long studentId) {
        try {
            courseService.unenrollStudent(courseId, studentId);
            return "redirect:/teacher/course-students/" + courseId;
        } catch (Exception e) {
            return "error";  
        }
    }
    
    
    @GetMapping("/manage-exam/{courseId}/{examId}")
    public String manageExam(
    		@PathVariable Long examId
    		,@PathVariable Long courseId
    		, Model model) {
    	
        Exam exam = examService.getExamById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        
        Course course = courseService.getCourseById(courseId)
        		.orElseThrow(() -> new RuntimeException("Course not found"));
        
        model.addAttribute("exam", exam);
        model.addAttribute("course", course);
        return "exam_draft"; 
    }
    
    @PostMapping("/update-exam")
    public String updateExam(@Valid @ModelAttribute ExamDto examDto, BindingResult result, Model model) {
    	
    	long examId = examDto.getExamId();
    	long courseId = examDto.getCourseId();
    			
        if (result.hasErrors()) {
            model.addAttribute("exam", examService.getExamById(examId).orElse(null));
            model.addAttribute("course", courseService.getCourseById(courseId).orElse(null));
            return "redirect:/teacher/manage-exam/" + courseId + "/" + examId;
        }

        Exam exam = examService.getExamById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (examDto.getExamDate().isBefore(LocalDate.now()) || examDto.getExamDate().isEqual(LocalDate.now())) {
            model.addAttribute("error", "La date de l'examen doit être antérieure à aujourd'hui.");
            model.addAttribute("exam", exam);
            model.addAttribute("course", course);
            return "manage_exam";
        }

        exam.setExamTitle(examDto.getExamTitle());
        exam.setExamDate(examDto.getExamDate());
        examService.updateExam(exam.getId(), exam.getExamTitle(), exam.getExamDate());  

        return "redirect:/teacher/manage-exam/" + courseId + "/" + examId; 
    }
    
    @PostMapping("/publish-exam/{examId}")
    public String publishExam(@PathVariable Long examId, Model model) {
    	
        boolean isPublished = examService.publishExam(examId);
        Exam exam = examService.getExamById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        if (isPublished) {
            model.addAttribute("success", "L'examen a été publié avec succès !");
        } else {
            model.addAttribute("error", "L'examen n'a pas pu être publié (soit déjà publié, soit introuvable).");
        }

        return "redirect:/teacher/manage-exam/"+ exam.getCourse().getId()+ "/" + examId;
    }
    
    
    @GetMapping("exam-results/{examId}")
    public String getResultsForm(@PathVariable Long examId, Model model) {
        Exam exam = examService.getExamById(examId)
        		.orElseThrow(() -> new RuntimeException("Exam not found"));

        if (exam == null) {
            model.addAttribute("error", "Examen non trouvé");
            return "error";
        }

        if (!exam.getExamStatus().name().equals("PUBLISHED")) {
            model.addAttribute("error", "L'examen n'est pas publié. Impossible de valider les résultats.");
            return "error";
        }

        List<Result> results = resultService.getResults(examId);
        model.addAttribute("exam", exam);
        model.addAttribute("results", results);
        model.addAttribute("resultDTOs", new ResultDto[results.size()]);
        return "exam_results";
    }
    
    
    @PostMapping("exam-results/validate/{examId}")
    public String validateResults(@PathVariable Long examId, @Valid @ModelAttribute("resultDTOs") List<ResultDto> resultDTOs,
                                  BindingResult resultBinding, Model model) {

        if (resultBinding.hasErrors()) {
            model.addAttribute("exam", examService.getExamById(examId));
            return "exam_results";
        }

        boolean success = resultService.validateResults(examId, resultDTOs);

        if (success) {
            model.addAttribute("success", "Les résultats ont été validés et l'examen est maintenant fermé.");
            return "redirect:/teacher/dashboard";  // Rediriger après la validation réussie
        } else {
            model.addAttribute("error", "Une erreur s'est produite lors de la validation des résultats.");
            return "exam_results";
        }
    }

    
    /*
     * ---------------------------------------------------------------------------------------------------------------------------------------------
     */
    
    
    @GetMapping("/create-question/{examId}")
    public String createQuestionForm(@PathVariable("examId") Long examId, Model model) {
        Exam exam = examService.getExamById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        
        model.addAttribute("isEdit", false);
        model.addAttribute("exam", exam);
        model.addAttribute("question", new Question()); 
        return "question_form"; 
    }
    
    @GetMapping("/update-question/{examId}/{questionId}")
    public String updateQuestionForm(@PathVariable("examId") Long examId, @PathVariable("questionId") Long questionId, Model model) {
        Exam exam = examService.getExamById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Question question = questionService.getQuestionById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        model.addAttribute("isEdit", true);
        model.addAttribute("exam", exam);
        model.addAttribute("question", question); 
        return "question_form";
    }

    @PostMapping("/save-question")
    public String saveQuestion(@ModelAttribute QuestionDto questionDto, Model model) {
    	
        Exam exam = examService.getExamById(questionDto.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Question question = new Question();
        question.setCategory(questionDto.getCategory());
        question.setDifficultyLevel(questionDto.getDifficultyLevel());
        question.setQuestionTitle(questionDto.getQuestionTitle());
        question.setOption1(questionDto.getOption1());
        question.setOption2(questionDto.getOption2());
        question.setOption3(questionDto.getOption3());
        question.setOption4(questionDto.getOption4());
        question.setRightAnswer(questionDto.getRightAnswer());
        question.setExam(exam);

        questionService.save(question); 

        return "redirect:/teacher/manage-exam/" + exam.getCourse().getId() + "/" + exam.getId(); 
    }
    
    @PostMapping("/update-question")
    public String updateQuestion(@ModelAttribute QuestionDto questionDto, Model model) {
        Exam exam = examService.getExamById(questionDto.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Question question = questionService.getQuestionById(questionDto.getId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setCategory(questionDto.getCategory());
        question.setDifficultyLevel(questionDto.getDifficultyLevel());
        question.setQuestionTitle(questionDto.getQuestionTitle());
        question.setOption1(questionDto.getOption1());
        question.setOption2(questionDto.getOption2());
        question.setOption3(questionDto.getOption3());
        question.setOption4(questionDto.getOption4());
        question.setRightAnswer(questionDto.getRightAnswer());

        questionService.save(question);

        return "redirect:/teacher/manage-exam/" + exam.getCourse().getId() + "/" + exam.getId(); 
    }

    
    
    @PostMapping("/delete-question/{examId}/{questionId}")
    public String deleteQuestion(@PathVariable Long examId, @PathVariable Long questionId) {
    	
        questionService.deleteQuestion(questionId);
        
        Exam exam = examService.getExamById(examId)
        		.orElseThrow(() -> new RuntimeException("Exam not found"));
        Long courseId = exam.getCourse().getId(); 
        
        return "redirect:/teacher/manage-exam/" + courseId + "/" + examId;
    }

    

}
