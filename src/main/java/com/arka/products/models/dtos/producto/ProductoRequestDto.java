package com.arka.products.models.dtos.producto;

import com.arka.products.models.dtos.categoria.CategoriaRequestDto;
import com.arka.products.models.dtos.marca.MarcaRequestDto;

public record ProductoRequestDto(
        String nombre,
        String descripcion,
        Double precio,
        Integer cantidadMinima,
        Integer cantidad,
        CategoriaRequestDto categoria,
        MarcaRequestDto marca
) {
}
