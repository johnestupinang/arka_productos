package com.arka.products.services;

import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.marca.MarcaResponseDto;

import java.util.List;
import java.util.Optional;

public interface IMarcaService {

    Optional<MarcaResponseDto> guardar (MarcaRequestDto marcaRequestDto);
    List<MarcaResponseDto> obtenerTodos ();
    Optional<MarcaResponseDto> obtenerMarcaPorId (Long id);
    Optional<MarcaResponseDto> actualizar (Long id, MarcaRequestDto marcaRequestDto);
    void eliminar (Long id);

}
