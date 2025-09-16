package com.arka.products.models.dtos.proveedor;

import com.arka.products.models.dtos.pais.PaisResponseDto;

public record ProveedorResponseDto(
        Long id,
        String nombre,
        String email,
        String telefono,
        String direccion,
        PaisResponseDto pais
) {}
