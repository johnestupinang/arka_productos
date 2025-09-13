package com.arka.products.repositories;

import com.arka.products.models.entities.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IMarcaRepository extends JpaRepository<Marca, Long> {
    boolean existsByNombre(String nombre);
    Optional<Marca> findByNombre(String nombre);
}
