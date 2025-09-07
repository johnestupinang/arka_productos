package com.arka.products.controllers;

import com.arka.products.models.dtos.pais.PaisRequestDto;
import com.arka.products.models.dtos.pais.PaisResponseDto;
import com.arka.products.services.IPaisService;
import com.arka.products.utilidades.Constantes;
import com.arka.products.utilidades.exceptions.PaisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.API_PAISES)
public class PaisController {

    @Autowired
    private final IPaisService iPaisService;

    public PaisController(IPaisService iPaisService) {
        this.iPaisService = iPaisService;
    }

    @PostMapping
    public ResponseEntity<PaisResponseDto> guardar(@RequestBody PaisRequestDto paisRequestDto){
        return iPaisService.guardar(paisRequestDto)
                .map(paisResponseDto -> {
                   return ResponseEntity
                           .status(HttpStatus.CREATED)
                           .body(paisResponseDto);
                })
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PaisResponseDto(0L, "","")));
    }

    @GetMapping
    public ResponseEntity<List<PaisResponseDto>> obtenerTodos(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iPaisService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisResponseDto> obtenerPorId(@PathVariable("id") Long id){
        return iPaisService.obtenerPaisPorId(id)
                .map(paisResponseDto -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(paisResponseDto))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new PaisResponseDto(0L,"","")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaisResponseDto> actualizar(@PathVariable("id") Long id, @RequestBody PaisRequestDto paisRequestDto){
        return iPaisService.actualizar(id, paisRequestDto)
                .map( paisResponseDto -> {
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(paisResponseDto);
                        }
                ).orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new PaisResponseDto(0L, "","")));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id){
        try{
            iPaisService.eliminar(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }catch (PaisException ex){
            return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
                    .build();
        }


    }

}
