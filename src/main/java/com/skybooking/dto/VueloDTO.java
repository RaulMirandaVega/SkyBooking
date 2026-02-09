package com.skybooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VueloDTO {

    private Long id;
    @NotBlank
    private String numeroVuelo;
    @NotBlank
    private String origen;
    @NotBlank
    private String destino;
    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaSalida;
    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaLlegada;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal precioTurista;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal precioBusiness;
    @NotNull
    private Long avionId;
    private String avionModelo;
    private String estado;

}