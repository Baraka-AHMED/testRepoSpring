package com.td.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departement_id", referencedColumnName = "id")
    private Departements departement;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicules vehicules;

    @ManyToMany
    @JoinTable(
            name = "person_competence",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private List<Competences> competences;
}