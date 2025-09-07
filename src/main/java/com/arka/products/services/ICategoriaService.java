package com.arka.products.services;

import com.arka.products.models.dtos.categoria.CategoriaRequestDto;
import com.arka.products.models.dtos.categoria.CategoriaResponseDto;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    Optional<CategoriaResponseDto> guardar (CategoriaRequestDto categoriaRequestDto);
    List<CategoriaResponseDto> obtenerTodos ();
    Optional<CategoriaResponseDto> obtenerCategoriaPorId (Long id);
    Optional<CategoriaResponseDto> actualizar (Long id, CategoriaRequestDto categoriaRequestDto);
    void eliminar (Long id);
}
