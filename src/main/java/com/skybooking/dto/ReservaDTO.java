package com.skybooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Long id;
    private String codigoReserva;
    private LocalDateTime fechaReserva;

    @NotNull
    private String clase; // TURISTA o BUSINESS

    private BigDecimal precioTotal;
    private String estado; // CONFIRMADA, CANCELADA, PENDIENTE_PAGO
    private String asiento;

    @NotNull
    private Long vueloId;

    @NotNull
    private Long pasajeroId;

    private String vueloNumero;
    private String pasajeroNombre;
}
