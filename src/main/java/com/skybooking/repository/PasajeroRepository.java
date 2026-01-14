package com.skybooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skybooking.model.Pasajero;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {

    Optional<Pasajero> findByDni(String dni);

    Optional<Pasajero> findByEmail(String email);

    @Query("SELECT p FROM Pasajero p " +
            "WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) " +
            "OR LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Pasajero> buscarPorNombre(@Param("termino") String termino);
}