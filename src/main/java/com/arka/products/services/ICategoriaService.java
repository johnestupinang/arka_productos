package com.arka.products.services;

import com.arka.products.models.dtos.categoria.CategoriaRequestDto;
import com.arka.products.models.dtos.categoria.CategoriaResponseDto;
import com.arka.products.models.dtos.producto.ProductoRequestDto;
import com.arka.products.models.entities.Categoria;

import java.util.List;

public interface ICategoriaService {
    CategoriaResponseDto guardar (CategoriaRequestDto categoriaRequestDto);
    List<CategoriaResponseDto> obtenerTodos ();
    CategoriaResponseDto obtenerCategoriaPorId (Long id);
    CategoriaResponseDto actualizar (Long id, CategoriaRequestDto categoriaRequestDto);
    void eliminar (Long id);
    boolean existePorNombre (String nombre);
    Categoria obtenerPorNombre (String nombre);
    Categoria validarCategoria(ProductoRequestDto productoRequestDto);
}
