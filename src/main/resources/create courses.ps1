# URL de votre API pour la création de cours
$baseUrl = "http://localhost:9001/api/courses/create"

# Liste des enseignants (en utilisant leurs ID que vous avez créés, il faut adapter les IDs selon votre base de données)
$teachers = @(
    1568,  # ID du premier enseignant
    1569,  # ID du deuxième enseignant
    1570,  # ID du troisième enseignant
    1571,  # ID du quatrième enseignant
    1572   # ID du cinquième enseignant
)

# Liste des titres de cours pour chaque enseignant
$courseTitles = @(
    "Mathematics 101",
    "Physics 101",
    "Chemistry 101",
    "Biology 101",
    "Computer Science 101"
)

# Boucle pour créer un cours pour chaque enseignant
for ($i = 0; $i -lt $teachers.Length; $i++) {
    $teacherId = $teachers[$i]
    $courseTitle = $courseTitles[$i]

    # Corps de la requête (JSON)
    $body = @{
        title = $courseTitle
        teacher = @{
            id = $teacherId
        }
    } | ConvertTo-Json

    # Requête HTTP POST pour créer le cours
    $response = Invoke-RestMethod -Uri $baseUrl -Method Post -ContentType "application/json" -Body $body

    # Affichage de la réponse
    if ($response) {
        Write-Host "Cours '$courseTitle' créé avec succès pour l'enseignant ID: $teacherId"
    } else {
        Write-Host "Erreur lors de la création du cours '$courseTitle' pour l'enseignant ID: $teacherId"
    }
}
