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

    //List<IntellectualPropertyDto> intellectualProperties
}
