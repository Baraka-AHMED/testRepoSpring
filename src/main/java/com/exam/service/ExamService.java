package com.exam.service;

import com.exam.model.Exam;
import com.exam.model.Course;
import com.exam.repository.ExamRepository;
import com.exam.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Récupère la liste de tous les examens.
     */
    public List<Exam> getAllExams() {
        return (List<Exam>) examRepository.findAll();
    }

    /**
     * Récupère un examen par son ID.
     */
    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }

    /**
     * Ajoute un nouvel examen après validation de l'existence du cours associé.
     */
    public void addExam(Exam exam) {
        if (exam.getCourse() == null || !courseRepository.existsById(exam.getCourse().getId())) {
            throw new IllegalArgumentException("Le cours associé à cet examen n'existe pas.");
        }
        examRepository.save(exam);
    }

    /**
     * Met à jour un examen existant.
     */
    public void updateExam(Exam exam) {
        if (!examRepository.existsById(exam.getId())) {
            throw new IllegalArgumentException("L'examen que vous essayez de mettre à jour n'existe pas.");
        }
        examRepository.save(exam);
    }

    /**
     * Supprime un examen par son ID après vérification de son existence.
     */
    public void deleteExamById(Long id) {
        if (!examRepository.existsById(id)) {
            throw new IllegalArgumentException("L'examen que vous essayez de supprimer n'existe pas.");
        }
        examRepository.deleteById(id);
    }

    public Exam getFirstExamByTeacher(String teacherName) {
        return examRepository.findFirstExamByTeacher(teacherName);
    }

	public List<Exam> findExamsByCourseId(Long courseId) {
		return examRepository.findExamsByCourseId(courseId);
	}

	public Exam findExamById(Long examId) {
		Exam exam = examRepository.findExamById(examId)
				.orElseThrow(()-> new RuntimeException("Exam not found."));
		return exam;
	}

	public List<Exam> findExamsByTeacherId(Long teacherId) {
		return examRepository.findExamsByTeacherId(teacherId);
	}

}
