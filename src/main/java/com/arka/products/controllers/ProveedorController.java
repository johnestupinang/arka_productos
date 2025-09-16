package com.arka.products.controllers;

import com.arka.products.models.dtos.proveedor.ProveedorRequestDto;
import com.arka.products.models.dtos.proveedor.ProveedorResponseDto;
import com.arka.products.services.IProveedorService;
import com.arka.products.utilidades.Constantes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.API_PROVEEDORES)
public class ProveedorController {

    private final IProveedorService proveedorService;

    public ProveedorController(IProveedorService proveedorService){
        this.proveedorService = proveedorService;
    }

    @PostMapping
    public ResponseEntity<ProveedorResponseDto> guardar (@Valid @RequestBody ProveedorRequestDto proveedorRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.guardar(proveedorRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponseDto>> obtenerTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(proveedorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDto> obtenerProveedorPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(proveedorService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody ProveedorRequestDto proveedorRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(proveedorService.actualizar(id, proveedorRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
