package com.arka.products.repositories;

import com.arka.products.models.entities.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMarcaRepository extends JpaRepository<Marca, Long> {

    boolean existsByNombre(String nombre);

}
