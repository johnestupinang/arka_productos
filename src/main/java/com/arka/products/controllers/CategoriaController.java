package com.arka.products.controllers;

import com.arka.products.models.dtos.categoria.CategoriaRequestDto;
import com.arka.products.models.dtos.categoria.CategoriaResponseDto;
import com.arka.products.services.ICategoriaService;
import com.arka.products.utilidades.Constantes;
import com.arka.products.utilidades.exceptions.CategoriaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.API_CATEGORIAS)
public class CategoriaController {

    @Autowired
    private final ICategoriaService iCategoriaService;

    public CategoriaController(ICategoriaService iCategoriaService){
        this.iCategoriaService = iCategoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> guardar (@RequestBody CategoriaRequestDto categoriaRequestDto){
        return iCategoriaService.guardar(categoriaRequestDto)
                .map(
                        categoriaResponseDto -> {
                            return ResponseEntity
                                    .status(HttpStatus.CREATED)
                                    .body(categoriaResponseDto);
                        }
                )
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CategoriaResponseDto(0L, "", "")));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> obtenerTodos(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iCategoriaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> obtenerPorId(@PathVariable("id") Long id){
        return iCategoriaService.obtenerCategoriaPorId(id)
                .map(categoriaResponseDto -> {
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(categoriaResponseDto);
                        }
                )
                .orElseGet(() -> {
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(new CategoriaResponseDto(0L, "", ""));
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> actualizar(@PathVariable("id") Long id, @RequestBody CategoriaRequestDto categoriaRequestDto){
        return iCategoriaService.actualizar(id, categoriaRequestDto)
                .map( categoriaResponsedto -> {
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(categoriaResponsedto);
                        }
                )
                .orElseGet(() -> {
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(new CategoriaResponseDto(0L, "", ""));

                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id){
        try {
            iCategoriaService.eliminar(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }catch (CategoriaException ex){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

    }

}
