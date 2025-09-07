package com.arka.products.services;

import com.arka.products.models.dtos.pais.PaisRequestDto;
import com.arka.products.models.dtos.pais.PaisResponseDto;

import java.util.List;
import java.util.Optional;

public interface IPaisService {

    Optional<PaisResponseDto> guardar (PaisRequestDto paisRequestDto);
    List<PaisResponseDto> obtenerTodos ();
    Optional<PaisResponseDto> obtenerPaisPorId (Long id);
    Optional<PaisResponseDto> actualizar (Long id, PaisRequestDto paisRequestDto);
    void eliminar (Long id);

}
