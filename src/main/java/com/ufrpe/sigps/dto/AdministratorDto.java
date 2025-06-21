package com.ufrpe.sigps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AdministratorDto extends UserDto {
    // Não tem campos adicionais além de UserDto, mas é útil para tipagem clara
}