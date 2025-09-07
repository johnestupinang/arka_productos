package com.arka.products.controllers;

import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.marca.MarcaResponseDto;
import com.arka.products.models.dtos.pais.PaisResponseDto;
import com.arka.products.services.IMarcaService;
import com.arka.products.utilidades.Constantes;
import com.arka.products.utilidades.exceptions.MarcaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.API_MARCAS)
public class MarcaController {

    @Autowired
    private final IMarcaService iMarcaService;

    public MarcaController(IMarcaService iMarcaService) {
        this.iMarcaService = iMarcaService;
    }

    @PostMapping
    public ResponseEntity<MarcaResponseDto> guardar (@RequestBody MarcaRequestDto marcaRequestDto){
        return iMarcaService.guardar(marcaRequestDto)
                .map(
                    marcaResponseDto -> {
                        return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(marcaResponseDto);
                    }
                )
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MarcaResponseDto(0L, "","", new PaisResponseDto(0L, "", ""))));
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponseDto>> obtenerTodos (){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iMarcaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponseDto> obtenerPorId (@PathVariable("id") Long id){
        return iMarcaService.obtenerMarcaPorId(id)
                .map( marcaResponseDto -> {
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(marcaResponseDto);
                })
                .orElseGet(() -> {
                            return ResponseEntity
                                    .status(HttpStatus.NOT_FOUND)
                                    .body(new MarcaResponseDto(0L,"", "", new PaisResponseDto(0L, "", "")));
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponseDto> actualizar (@PathVariable("id") Long id, @RequestBody MarcaRequestDto marcaRequestDto){
        return iMarcaService.actualizar(id, marcaRequestDto)
                .map(
                        marcaResponseDto -> {
                            return ResponseEntity
                                    .status(HttpStatus.OK)
                                    .body(marcaResponseDto);
                        }
                )
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new MarcaResponseDto(0L,"","", new PaisResponseDto(0L, "", ""))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar (@PathVariable("id") Long id) {
        try {
            iMarcaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (MarcaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
