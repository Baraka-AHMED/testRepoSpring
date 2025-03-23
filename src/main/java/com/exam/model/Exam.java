package com.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String examTitle;
    
    @Column(nullable = false)
    private LocalDate examDate;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @JsonIgnore
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();
    
    @OneToMany(mappedBy = "exam")
    private List<Result> results = new ArrayList<>();
    
    @Enumerated(EnumType.STRING) 
    private ExamStatus examStatus;
    
    @PrePersist
    public void prePersist() {
        this.examStatus = ExamStatus.DRAFT;  // Par défaut, un examen est en mode "élaboration"
    }
    
    /*
     * -----------------------------------------------------------------------------------------------------------------------------------
     */
    
    // Vérifier et mettre à jour le statut de l'examen
    public void updateStatus() {
        if (results.isEmpty() && LocalDate.now().isBefore(examDate)) {
            this.examStatus = ExamStatus.PUBLISHED; // L'examen est publié
        } else if (!results.isEmpty() && LocalDate.now().isAfter(examDate)) {
            this.examStatus = ExamStatus.CLOSED; // L'examen est clôturé après correction
        }
    }

    public void addResult(Result result) {
        this.results.add(result);
        updateStatus(); // Met à jour le statut automatiquement
    }
    
}
