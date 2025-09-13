package com.arka.products.services;

import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.pais.PaisRequestDto;
import com.arka.products.models.dtos.pais.PaisResponseDto;
import com.arka.products.models.entities.Pais;

import java.util.List;

public interface IPaisService {
    PaisResponseDto guardar (PaisRequestDto paisRequestDto);
    List<PaisResponseDto> obtenerTodos ();
    PaisResponseDto obtenerPaisPorId (Long id);
    PaisResponseDto actualizar (Long id, PaisRequestDto paisRequestDto);
    void eliminar (Long id);
    Pais validarPais(MarcaRequestDto marcaRequestDto);
}
