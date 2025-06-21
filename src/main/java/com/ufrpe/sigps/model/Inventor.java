package com.ufrpe.sigps.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true) // Inclui campos da superclasse no equals e hashCode
@Entity
@Table(name = "inventor")
@DiscriminatorValue("INVENTOR") // Valor para a coluna 'user_type'
public class Inventor extends User {

    @Column(nullable = false)
    private String course; // Curso ao qual pertence (se for aluno ou professor/técnico vinculado a um curso)

    @Column(nullable = false)
    private String department; // Departamento ao qual pertence

    @OneToMany(mappedBy = "inventor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IntellectualProperty> intellectualProperties; // Um inventor pode ter várias PIs
}