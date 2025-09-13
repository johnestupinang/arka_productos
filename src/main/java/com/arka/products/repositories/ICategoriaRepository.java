package com.arka.products.repositories;

import com.arka.products.models.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNombre(String nombre);
    Optional<Categoria> findByNombre(String nombre);
}
