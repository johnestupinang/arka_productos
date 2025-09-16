package com.arka.products.repositories;

import com.arka.products.models.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor, Long> {
    boolean existsProveedorByNombre(String nombre);
}
