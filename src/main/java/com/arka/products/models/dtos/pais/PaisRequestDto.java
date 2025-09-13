package com.arka.products.models.dtos.pais;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PaisRequestDto(

        @NotBlank(message = "El nombre del pais no puede ser vacio o nulo.")
        @Size(min = 1, max = 255, message = "El nombre del pais debe comprender entre 3 y 6 caracteres.")
        String nombre,

        @NotBlank(message = "El codigo del pais no puede ser vacio o nulo.")
        @Size(min = 3, max = 6, message = "El codigo del pais debe comprender entre 3 y 6 caracteres.")
        String codigoPais

) {}
