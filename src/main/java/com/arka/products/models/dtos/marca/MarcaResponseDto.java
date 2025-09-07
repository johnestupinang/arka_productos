package com.arka.products.models.dtos.marca;

import com.arka.products.models.dtos.pais.PaisResponseDto;

public record MarcaResponseDto(Long id, String nombre, String descripcion, PaisResponseDto pais) {
}
