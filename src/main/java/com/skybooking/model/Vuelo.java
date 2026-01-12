package com.skybooking.model;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="vuelos")
public class Vuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "numero_vuelo", unique = true, nullable = false)
    private String numeroVuelo;
    @Column(nullable = false)
    private String origen;
    @Column(nullable = false)
    private String destino;
    @Column(name = "fecha_salida", nullable = false)
    private LocalDateTime fechaSalida;
    @Column(name = "fecha_llegada", nullable = false)
    private LocalDateTime fechaLlegada;
    @Column(name = "precio_turista", nullable = false)
    private BigDecimal precioTurista;
    @Column(name = "precio_business", nullable = false)
    private BigDecimal precioBusiness;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoVuelo estado;
    @ManyToOne
    @JoinColumn(name = "avion_id", nullable = false)
    private Avion avion;
    @OneToMany(mappedBy = "vuelo", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
}