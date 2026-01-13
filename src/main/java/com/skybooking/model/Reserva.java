package com.skybooking.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDate;
import java.math.BigDecimal;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "codigo_reserva", nullable = false, unique = "true")
    private String codigoReserva;
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;
    @Enumerated(EnumType.STRING)
    @Column()
    private ClaseReserva clase;
    @Column(name = "precio_total", nullable = false)
    private BigDecimal precioTotal;
    @Enumerated(EnumType.STRING)
    @Column()
    private EstadoReserva estado;
    @ManyToOne
    @JoinColumn(name = "avion_id")
    private Avion avion;
    @ManyToOne
    @JoinColumn(name = "pasajero_id")
    private Pasajero pasajero;
}