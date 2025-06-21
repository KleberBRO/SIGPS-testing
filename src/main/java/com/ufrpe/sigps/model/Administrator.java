package com.ufrpe.sigps.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "administrator")
@DiscriminatorValue("ADMIN") // Valor para a coluna 'user_type'
public class Administrator extends User {
    // Administradores não possuem atributos específicos além dos de User,
    // mas a classe existe para diferenciar o tipo de usuário e a hierarquia JPA.
}