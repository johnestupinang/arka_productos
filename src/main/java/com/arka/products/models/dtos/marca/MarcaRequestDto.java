package com.arka.products.models.dtos.marca;

import com.arka.products.models.dtos.pais.PaisRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MarcaRequestDto(

        @NotBlank(message = "El nombre de la marca no puede ser vacio o nulo.")
        @Size(min = 1, max = 255, message = "El nombre de la marca debe comprender entre 1 y 255 caracteres.")
        String nombre,

        @NotBlank(message = "La descripcion de la marca no puede ser vacio o nulo.")
        @Size(min = 1, max = 255, message = "La descripcion de la marca debe comprender entre 1 y 255 caracteres.")
        String descripcion,

        @NotNull(message = "El pais no puede ser nulo.")
        @Valid PaisRequestDto pais

) {}
