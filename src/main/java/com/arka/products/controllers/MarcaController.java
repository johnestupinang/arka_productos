package com.arka.products.controllers;

import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.marca.MarcaResponseDto;
import com.arka.products.services.IMarcaService;
import com.arka.products.utilidades.Constantes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.API_MARCAS)
@RequiredArgsConstructor
public class MarcaController {

    private final IMarcaService iMarcaService;

    @PostMapping
    public ResponseEntity<MarcaResponseDto> guardar (@Valid @RequestBody MarcaRequestDto marcaRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iMarcaService.guardar(marcaRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponseDto>> obtenerTodos (){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iMarcaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponseDto> obtenerPorId (@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iMarcaService.obtenerMarcaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponseDto> actualizar (@PathVariable("id") Long id, @Valid @RequestBody MarcaRequestDto marcaRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(iMarcaService.actualizar(id, marcaRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable("id") Long id) {
        iMarcaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
