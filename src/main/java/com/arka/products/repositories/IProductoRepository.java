package com.arka.products.repositories;

import com.arka.products.models.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<Producto, Long> {
}
