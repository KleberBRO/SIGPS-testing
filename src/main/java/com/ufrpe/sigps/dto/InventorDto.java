package com.ufrpe.sigps.dto;

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
public class InventorDto extends UserDto {
    private String course;
    private String department;

    // Opcional: List<IntellectualPropertyDto> intellectualProperties;
    // Cuidado com a recursão se incluir PIs aqui. Pode ser melhor ter um endpoint separado.
}
