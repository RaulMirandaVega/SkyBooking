package com.skybooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skybooking.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Optional<Reserva> findByCodigoReserva(String codigo);

    List<Reserva> findByPasajeroId(Long pasajeroId);

    List<Reserva> findByVueloId(Long vueloId);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.vuelo.id = :vueloId AND r.estado = 'CONFIRMADA'")
    Long contarReservasConfirmadas(@Param("vueloId") Long vueloId);
}
