package com.arka.products.repositories;

import com.arka.products.models.entities.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaisRepository extends JpaRepository <Pais, Long>{
}
