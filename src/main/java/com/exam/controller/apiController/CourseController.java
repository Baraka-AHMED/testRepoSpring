package com.exam.controller.apiController;

import com.exam.dto.CourseDto;
import com.exam.model.Course;
import com.exam.model.User;
import com.exam.service.CourseService;
import com.exam.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;
    
    @Autowired
    private UserService userService;

    // Liste tous les cours
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // Obtenir un cours par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Ajouter un nouveau cours
    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@RequestBody CourseDto courseDto) {
    	
       User teacher = userService.getUserById(courseDto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setTeacher(teacher);

        courseService.addCourse(course);

        return ResponseEntity.status(201).body(course);
    }

    // Mettre à jour un cours
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Optional<Course> existingCourse = courseService.getCourseById(id);
        if (existingCourse.isPresent()) {
            course.setId(id); // Assurer que l'ID est celui de l'entité existante
            courseService.updateCourse(course);
            return ResponseEntity.ok(course);  // Retourne le cours mis à jour
        }
        return ResponseEntity.notFound().build();  // Si le cours n'existe pas
    }

    // Supprimer un cours par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        Optional<Course> existingCourse = courseService.getCourseById(id);
        if (existingCourse.isPresent()) {
            courseService.deleteCourseById(id);
            return ResponseEntity.noContent().build();  // Retourne un status 204 (No Content)
        }
        return ResponseEntity.notFound().build();  // Si le cours n'existe pas
    }

    // Lister les cours d'un enseignant spécifique
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getCoursesByTeacherId(@PathVariable Long teacherId) {
        List<Course> courses = courseService.findCoursesByTeacherId(teacherId);
        return ResponseEntity.ok(courses);
    }

    // Inscrire un étudiant à un cours
    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudentInCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        courseService.enrollStudentInCourse(studentId, courseId);
        return ResponseEntity.ok("Student enrolled successfully");
    }
}
