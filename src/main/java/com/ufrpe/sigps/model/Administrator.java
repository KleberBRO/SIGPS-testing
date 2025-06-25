package com.ufrpe.sigps.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "administrator")
@DiscriminatorValue("ADMIN") // Valor para a coluna 'user_type'
public class Administrator extends User {
}