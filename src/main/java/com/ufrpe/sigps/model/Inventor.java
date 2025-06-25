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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "inventor")
@DiscriminatorValue("INVENTOR")
public class Inventor extends User {

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private String department;

    @OneToMany(mappedBy = "inventor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IntellectualProperty> intellectualProperties;
}