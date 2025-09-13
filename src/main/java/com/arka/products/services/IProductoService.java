package com.arka.products.services;

import com.arka.products.models.dtos.producto.ProductoRequestDto;
import com.arka.products.models.dtos.producto.ProductoResponseDto;

import java.util.List;

public interface IProductoService {
    ProductoResponseDto guardar (ProductoRequestDto productoRequestDto);
    List<ProductoResponseDto> obtenerTodos ();
    ProductoResponseDto obtenerProductoPorId (Long id);
    ProductoResponseDto actualizar (Long id, ProductoRequestDto producto);
    void eliminar (Long id);
    boolean existsProductoByNombre(ProductoRequestDto productoRequestDto);
}
