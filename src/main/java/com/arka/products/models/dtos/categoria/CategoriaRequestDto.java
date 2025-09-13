package com.arka.products.models.dtos.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDto(

        @NotBlank(message = "Nombre de la categoria no puede ser vacia o nula.")
        @Size(min = 1, max = 255, message = "Nombre de la categoria debe comprender entre 1 y 255 caracteres.")
        String nombre,

        @NotBlank(message = "Descripción de la categoria no puede ser vacia o nula.")
        @Size(min = 1, max = 255, message = "Descripcion de la categoria debe comprender entre 1 y 255 caracteres.")
        String descripcion

) {}
