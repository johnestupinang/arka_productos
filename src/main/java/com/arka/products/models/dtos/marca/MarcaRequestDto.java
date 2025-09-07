package com.arka.products.models.dtos.marca;

import com.arka.products.models.dtos.pais.PaisRequestDto;

public record MarcaRequestDto(String nombre, String descripcion, PaisRequestDto pais) {
}
