package com.exam.model;

public enum ExamStatus {
    DRAFT,      // Examen en préparation (pas encore accessible aux étudiants)
    PUBLISHED,  // Examen accessible aux étudiants
    CLOSED      // Examen terminé avec résultats publiés
}