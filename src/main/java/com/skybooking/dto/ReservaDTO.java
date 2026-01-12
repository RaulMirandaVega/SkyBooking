package com.skybooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservaDTO {

    private long id;
    private String codigoReserva;
    private LocalDateTime fechaReserva;
    @NotNull
    private enum clase{
        CONFIRMADA,
        CANCELADA,
        PENDIENTE_PAGO
    }
    private BigDecimal precioTotal;
    private String estado;
    private String asiento;
    @NotNull
    private Long vueloId;
    @NotNull
    private Long pasajeroId;
    private int vueloNumero;
    private String pasajeroNombre;

}