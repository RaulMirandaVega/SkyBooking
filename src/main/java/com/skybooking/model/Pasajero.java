package com.skybooking.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pasajeros")
public class Pasajero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    @Column(nullable = false, unique = true)
    private String dni;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = true)
    private String telefono;
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    @OneToMany(mappedBy = "pasajero")
    private List<Reserva> reservas;
}