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

