package com.skybooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvionDTO {

    private Long id;
    private String matricula;
    private String modelo;
    @Min(value = 1)
    private Integer capacidadTurista;
    @NotNull
    @Min(value = 0)
    private Integer capacidadBusiness;

}