package com.arka.products.controllers;

import com.arka.products.models.dtos.categoria.CategoriaRequestDto;
import com.arka.products.models.dtos.categoria.CategoriaResponseDto;
import com.arka.products.services.ICategoriaService;
import com.arka.products.utilidades.Constantes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.API_CATEGORIAS)
@RequiredArgsConstructor
public class CategoriaController {

    private final ICategoriaService iCategoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> guardar (@Valid @RequestBody CategoriaRequestDto categoriaRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body( iCategoriaService.guardar(categoriaRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> obtenerTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(iCategoriaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> obtenerPorId(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iCategoriaService.obtenerCategoriaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> actualizar(@PathVariable("id") Long id, @Valid @RequestBody CategoriaRequestDto categoriaRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(iCategoriaService.actualizar(id, categoriaRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id){
            iCategoriaService.eliminar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
