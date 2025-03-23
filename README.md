# 🎓 Exam Management System – Spring Boot

Projet réalisé dans le cadre de l'UE **Architecture et Programmation par Composants** (M2 MIAGE 2ID)  
**Enseignant :** Davide Guastella  
**Date de rendu :** 23 mars 2025

## 🚀 Objectif

Ce projet a pour but de faciliter la gestion des examens au sein de la Faculté d'Économie et de Gestion. Il permet aux enseignants de planifier les examens, aux étudiants de consulter les examens disponibles et de s’inscrire aux cours, et aux administrateurs de gérer les utilisateurs de manière sécurisée.

---

## 🛠️ Technologies utilisées

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- H2 Database (pour les tests)
- Thymeleaf (pour les vues web)
- Maven

---

## 📦 Fonctionnalités principales

### ✅ Utilisateurs et rôles
- 3 rôles : `ADMIN`, `TEACHER`, `STUDENT`
- Authentification par login/email + mot de passe
- Gestion des rôles via l'interface admin

### 📚 Gestion des cours
- Ajout, modification, suppression de cours (enseignants)
- Inscription des étudiants aux cours
- Lien cours ↔ examens

### 📝 Gestion des examens
- Création d'examens liés à un cours par les enseignants
- Ajout/modification/suppression de questions à un examen
- Visualisation des examens pour chaque étudiant inscrit à un cours

### 📋 Gestion des utilisateurs
- Interface d'administration pour voir/modifier/supprimer les utilisateurs
- Inscription libre (sauf ADMIN)

### 🔐 Sécurité
- Spring Security avec gestion des rôles
- BCrypt pour le hachage des mots de passe
- Redirection en fonction du rôle

---

## 🌐 API REST Principales

### 🔍 Examens
- `GET /exams/all` – Liste tous les examens
- `POST /exams/add` – Ajouter un examen
- `GET /exams/find?id={id}` – Trouver un examen
- `DELETE /exams/deleteById?id={id}` – Supprimer un examen

### 👨‍🏫 Utilisateurs
- `GET /users/all` – Tous les utilisateurs
- `GET /users/role?role=TEACHER` – Utilisateurs par rôle
- `POST /users/add` – Ajouter un utilisateur

### ❓ Quiz
- `GET /quizzes/all` – Liste de tous les quiz

---

🗂 Structure du projet
pgsql
Copier
Modifier
src/
├── controller/     → Contrôleurs REST et MVC
├── model/          → Entités JPA (User, Course, Exam, Question, Quiz)
├── repository/     → Repositories JPA
├── service/        → Logique métier
├── dto/            → Objets de transfert (Login, Register)
├── config/         → Sécurité (Spring Security)
├── view/           → Contrôleur racine (ViewController)
└── Main.java       → Point d’entrée de l’application

Guide d'utilisation de l'application
📂 Fichiers de la base de données

Les fichiers de la base de données (mydb.trace.db et mydb.mv.db) se trouvent dans le dossier data du projet. Cette base contient déjà les entités users, courses, exams, etc.
🔑 Connexion à l'application
Authentification via l'API

    URL : http://localhost:9001/api/login

    Méthode : POST

    Corps de la requête :

    {
      "usernameOrEmail": "root",
      "password": "root"
    }

Identifiants disponibles

Les identifiants sont définis dans les scripts PowerShell du projet :

    create_users.ps1

    create_courses.ps1
    Consultez ces fichiers pour voir les comptes existants et leurs mots de passe.

🎓 Gestion des utilisateurs via API
Créer un nouvel utilisateur

    URL : http://localhost:9001/api/register

    Méthode : POST

    Corps de la requête :

    {
      "username": "benjamin_student",
      "email": "benjamin.student@example.com",
      "firstName": "Benjamin",
      "lastName": "White",
      "password": "1234",
      "role": "STUDENT"
    }

📚 Gestion des cours via API
Créer un cours et l'associer à un enseignant

    URL : http://localhost:9001/api/courses/create

    Méthode : POST

    Corps de la requête :

    {
      "title": "Mathematics 101",
      "teacher": {
        "id": 1568
      }
    }

    Remplacez 1568 par l'ID d'un enseignant existant.

Lister tous les cours disponibles

    URL : http://localhost:9001/api/courses

    Méthode : GET

📂 Emplacement des fichiers du projet

    Base de données : data/

    Scripts de création d'utilisateurs : scripts/create_users.ps1

    Scripts de création de cours : scripts/create_courses.ps1

