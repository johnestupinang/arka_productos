package com.arka.products.services;

import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.marca.MarcaResponseDto;
import com.arka.products.models.dtos.producto.ProductoRequestDto;
import com.arka.products.models.entities.Marca;

import java.util.List;

public interface IMarcaService {
    MarcaResponseDto guardar (MarcaRequestDto marcaRequestDto);
    List<MarcaResponseDto> obtenerTodos ();
    MarcaResponseDto obtenerMarcaPorId (Long id);
    MarcaResponseDto actualizar (Long id, MarcaRequestDto marcaRequestDto);
    void eliminar (Long id);
    boolean existePorNombre (String nombre);
    Marca obtenerPorNombre (String nombre);
    Marca validarMarca(ProductoRequestDto productoRequestDto);
}
