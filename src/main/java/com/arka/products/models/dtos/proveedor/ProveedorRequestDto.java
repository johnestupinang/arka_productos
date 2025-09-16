package com.arka.products.models.dtos.proveedor;

import com.arka.products.models.dtos.pais.PaisRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProveedorRequestDto(
        @NotBlank(message = "El nombre del proveedor no puede estar vacío")
        @Size(min = 1, max = 100, message = "El nombre del proveedor debe comprender entre 1 y 100 caracteres")
        String nombre,

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email debe tener un formato válido")
        @Size(max = 100, message = "El email no puede superar los 100 caracteres")
        String email,

        @Pattern(
                regexp = "^[0-9+\\-\\s]{7,15}$",
                message = "El teléfono solo puede contener números, espacios, '+' o '-' y debe tener entre 7 y 15 dígitos"
        )
        String telefono,

        @NotBlank(message = "La direccion del proveedor no debe ser vacia")
        @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
        String direccion,

        @NotNull(message = "El país del proveedor es obligatorio")
        @Valid
        PaisRequestDto pais
) {}
