package com.skybooking.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Data
@AllArgsContructor
@NoArgsContructor
@Entity(name="aviones")
public class Avion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column(unique = true, nullable = false)
    private String matricula;
    @Column(nullable = true)
    private String modelo;
    @Column(name = "capacidad_turista", nullable = false)
    private Integer capacidadTurista;
    @Column(name = "capacidad_business", nullable = false)
    private Integer capacidadBusiness;
    @OneToMany(mappedBy = "avion", cascade = Cascade.ALL)
    private List<Vuelo> vuelos;
}