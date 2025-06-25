package com.ufrpe.sigps.dto;

import com.ufrpe.sigps.model.Role;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto {

    private Long id;
    private String name;
    private LocalDate dateBirth;
    private String email;
    private String cpf;
    private String nationality;
    private String address;
    private Role role;
}