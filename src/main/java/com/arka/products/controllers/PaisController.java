package com.arka.products.controllers;

import com.arka.products.models.dtos.pais.PaisRequestDto;
import com.arka.products.models.dtos.pais.PaisResponseDto;
import com.arka.products.services.IPaisService;
import com.arka.products.utilidades.Constantes;
import com.arka.products.utilidades.exceptions.PaisException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.API_PAISES)
@RequiredArgsConstructor
public class PaisController {

    private final IPaisService iPaisService;

    @PostMapping
    public ResponseEntity<PaisResponseDto> guardar(@Valid @RequestBody PaisRequestDto paisRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPaisService.guardar(paisRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<PaisResponseDto>> obtenerTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(iPaisService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisResponseDto> obtenerPorId(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(iPaisService.obtenerPaisPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaisResponseDto> actualizar(@PathVariable("id") Long id, @Valid @RequestBody PaisRequestDto paisRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(iPaisService.actualizar(id, paisRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id){
        iPaisService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
