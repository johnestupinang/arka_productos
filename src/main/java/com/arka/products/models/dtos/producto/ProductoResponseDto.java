package com.arka.products.models.dtos.producto;

import com.arka.products.models.dtos.categoria.CategoriaResponseDto;
import com.arka.products.models.dtos.marca.MarcaResponseDto;

import java.time.LocalDateTime;

public record ProductoResponseDto(
        Long id,
        String nombre,
        String descripcion,
        Double precio,
        Integer cantidadMinima,
        Integer cantidad,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion,
        CategoriaResponseDto categoria,
        MarcaResponseDto marca
) {
}
