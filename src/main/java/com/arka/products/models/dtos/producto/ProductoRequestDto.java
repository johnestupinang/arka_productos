package com.arka.products.models.dtos.producto;

import com.arka.products.models.dtos.categoria.CategoriaRequestDto;
import com.arka.products.models.dtos.marca.MarcaRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record ProductoRequestDto(

        @NotBlank(message = "El nombre del producto no puede ser nulo o vacio.")
        @Size(min = 1, max = 255, message = "El nombre del producto debe estar comprendido entre 1 y 255 caracteres.")
        String nombre,

        @NotBlank(message = "La descripcion del producto no puede ser nulo o vacio.")
        @Size(min = 1, max = 255, message = "La descripcion del producto debe estar comprendido entre 1 y 255 caracteres.")
        String descripcion,

        @NotNull(message = "El precio no puede ser nulo.")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0.")
        Double precio,

        @NotNull(message = "La cantidad mínima no puede ser nula.")
        @Min(value = 5, message = "La cantidad mínima debe ser mayor o igual a 0.")
        Integer cantidadMinima,

        @NotNull(message = "La cantidad no puede ser nula.")
        @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0.")
        Integer cantidad,

        @NotNull(message = "La categoría no puede ser nula.")
        @Valid
        CategoriaRequestDto categoria,

        @NotNull(message = "La marca no puede ser nula.")
        @Valid
        MarcaRequestDto marca

) {}
