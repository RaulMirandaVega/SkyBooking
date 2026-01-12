package com.skybooking.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="aviones")
public class Avion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(unique = true, nullable = false)
    private String matricula;
    @Column(nullable = false)
    private String modelo;
    @Column(name = "capacidad_turista", nullable = false)
    private Integer capacidadTurista;
    @Column(name = "capacidad_business", nullable = false)
    private Integer capacidadBusiness;
    @OneToMany(mappedBy = "avion")
    private List<Vuelo> vuelos;
}