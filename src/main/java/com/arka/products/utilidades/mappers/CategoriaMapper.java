package com.arka.products.utilidades.mappers;

import com.arka.products.models.dtos.categoria.CategoriaRequestDto;
import com.arka.products.models.dtos.categoria.CategoriaResponseDto;
import com.arka.products.models.entities.Categoria;

public class CategoriaMapper {

    public static Categoria deCategoriaRequestDtoACategoria (CategoriaRequestDto categoriaRequestDto) {
        if (categoriaRequestDto == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaRequestDto.nombre());
        categoria.setDescripcion(categoriaRequestDto.descripcion());
        return categoria;
    }

    public static CategoriaResponseDto deCategoriaACategoriaResponseDto (Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        return new CategoriaResponseDto(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }

    public static Categoria deCategoriaResponseDtoACategoria (CategoriaResponseDto categoriaResponseDto) {
        if (categoriaResponseDto == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(categoriaResponseDto.id());
        categoria.setNombre(categoriaResponseDto.nombre());
        categoria.setDescripcion(categoriaResponseDto.descripcion());
        return categoria;
    }
}
