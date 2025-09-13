package com.arka.products.repositories;

import com.arka.products.models.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByNombre(String nombre);
}
