package com.skybooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skybooking.model.Avion;

@Repository
public interface AvionRepository extends JpaRepository<Avion, Long> {

    Optional<Avion> findByMatricula(String matricula);

    List<Avion> findByModelo(String modelo);
}
