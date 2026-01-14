package com.skybooking.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skybooking.model.Vuelo;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    Optional<Vuelo> findByNumeroVuelo(String numeroVuelo);

    List<Vuelo> findByOrigenAndDestino(String origen, String destino);

    List<Vuelo> findByAvionId(Long avionId);

    @Query("SELECT v FROM Vuelo v WHERE v.fechaSalida BETWEEN :inicio AND :fin")
    List<Vuelo> buscarVuelosPorFecha(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );

    @Query("SELECT v FROM Vuelo v WHERE v.estado = 'PROGRAMADO' AND v.fechaSalida > :ahora")
    List<Vuelo> buscarVuelosDisponibles(
            @Param("ahora") LocalDateTime ahora
    );
}
