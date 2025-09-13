package com.arka.products.controllers;

import com.arka.products.models.dtos.producto.ProductoRequestDto;
import com.arka.products.models.dtos.producto.ProductoResponseDto;
import com.arka.products.services.IProductoService;
import com.arka.products.utilidades.Constantes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.API_PRODUCTOS)
@RequiredArgsConstructor
public class ProductoController {

    private final IProductoService iProductoService;

    @PostMapping
    public ResponseEntity<ProductoResponseDto> guardar(@Valid @RequestBody ProductoRequestDto productoRequestDto) {
        var productoGuardado = iProductoService.guardar(productoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> obtenerTodos() {
        var productos = iProductoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> obtenerProductoPorId(@PathVariable Long id) {
        var producto = iProductoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDto productoRequestDto) {
        var productoActualizado = iProductoService.actualizar(id, productoRequestDto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        iProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
