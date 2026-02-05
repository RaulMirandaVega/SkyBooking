package com.skybooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasajeroDTO {

    private Long id;
    private String nombre;
    private String apellidos;
    @Pattern(regexp = "^[0-9]{8}[A-Z]$")
    private String dni;
    @Email
    private String email;
    private String telefono;
    @Past
    private LocalDate fechaNacimiento;

}