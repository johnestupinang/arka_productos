package com.arka.products.services;

import com.arka.products.models.dtos.proveedor.ProveedorRequestDto;
import com.arka.products.models.dtos.proveedor.ProveedorResponseDto;

import java.util.List;

public interface IProveedorService {
    ProveedorResponseDto guardar (ProveedorRequestDto proveedorRequestDto);
    List<ProveedorResponseDto> obtenerTodos();
    ProveedorResponseDto obtenerPorId(Long id);
    ProveedorResponseDto actualizar (Long id, ProveedorRequestDto proveedorRequestDto);
    void eliminar(Long id);
}
