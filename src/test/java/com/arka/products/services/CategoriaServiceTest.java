package com.arka.products.services;

import com.arka.products.models.entities.Categoria;
import com.arka.products.repositories.ICategoriaRepository;
import com.arka.products.services.impl.CategoriaServiceImpl;
import com.arka.products.utilidades.Utilidades;
import com.arka.products.utilidades.exceptions.CategoriaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    Utilidades util = new Utilidades();
    List<Categoria> categorias;

    @BeforeEach
    void setUp() {
        categorias = new ArrayList<>();
    }

    @Mock
    ICategoriaRepository iCategoriaRepository;

    @InjectMocks
    CategoriaServiceImpl categoriaService;

    @Test
    void test_debeGuardarCategoria_Exitoso() {
        var categoria = util.categoriaDummy();
        var categoriaRequestDto = util.categoriaRequestDtoDummy();
        when(iCategoriaRepository.existsByNombre(anyString())).thenReturn(false);
        when(iCategoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        var categoriaEsperada = categoriaService.guardar(categoriaRequestDto);
        Assertions.assertEquals(categoriaEsperada.nombre(), categoriaRequestDto.nombre());
    }

    @Test
    void test_debeGenerarExceptionAlGuardarCategoria() {
        when(iCategoriaRepository.existsByNombre(anyString())).thenReturn(true);
        Assertions.assertThrows(CategoriaException.class, () -> {
            categoriaService.guardar(util.categoriaRequestDtoDummy());
        });
    }

    @Test
    void test_debeObtenerListaCategorias_Exitoso() {
        var categorias = util.listaCategorias();
        when(iCategoriaRepository.findAll()).thenReturn(categorias);
        var categoriasEsperada = categoriaService.obtenerTodos();
        Assertions.assertEquals(categorias.size(), categoriasEsperada.size());
    }

    @Test
    void test_debeGenerarExceptionAlObtenerTodasLasCategorias() {
        when(iCategoriaRepository.findAll()).thenReturn(categorias);
        Assertions.assertThrows(CategoriaException.class, () -> {
            categoriaService.obtenerTodos();
        });
    }

    @Test
    void test_debeObtenerCategoriaPorid(){
        var categoria = util.categoriaDummy();
        when(iCategoriaRepository.findById(anyLong())).thenReturn(Optional.of(categoria));
        var categoriaEsperada = categoriaService.obtenerCategoriaPorId(anyLong());
        Assertions.assertEquals(categoria.getNombre(), categoriaEsperada.nombre());
    }

    @Test
    void test_debeGenerarExceptionAlBuscarCategoriaPorId(){
        when(iCategoriaRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(CategoriaException.class, () -> {
            categoriaService.obtenerCategoriaPorId(anyLong());
        });
    }

    @Test
    void test_debeActualizarCategoria(){
        var categoria = util.categoriaDummy();
        var categoriaRequestDto = util.categoriaRequestDtoDummy();
        when(iCategoriaRepository.findById(anyLong())).thenReturn(Optional.of(categoria));
        when(iCategoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        var categoriaEsperada = categoriaService.actualizar(anyLong(), categoriaRequestDto);
        Assertions.assertEquals(categoria.getNombre(), categoriaEsperada.nombre());
    }

    @Test
    void test_debeGenerarExceptionAlTratarDeActualizar(){
        when(iCategoriaRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(CategoriaException.class, () -> {
            categoriaService.actualizar(anyLong(), util.categoriaRequestDtoDummy());
        });
    }

    @Test
    void test_debeEliminarCategoria(){
        var categoria = util.categoriaDummy();
        when(iCategoriaRepository.findById(anyLong())).thenReturn(Optional.of(categoria));
        categoriaService.eliminar(anyLong());
        Mockito.verify(iCategoriaRepository, times(1)).delete(categoria);
    }

    @Test
    void test_debeGenerarExceptionAlTratarEliminarCategoria(){
        when(iCategoriaRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(CategoriaException.class, () -> {
            categoriaService.eliminar(anyLong());
        });
    }

    @Test
    void test_debeObtenerCategoriaPorNombre(){
        var categoria = util.categoriaDummy();
        when(iCategoriaRepository.findByNombre(anyString())).thenReturn(Optional.of(categoria));
        var categoriaEsperada = categoriaService.obtenerPorNombre(anyString());
        Assertions.assertEquals(categoria.getNombre(), categoriaEsperada.getNombre());
    }

    @Test
    void test_debeValidarCategoriaIfCorrecto(){
        var categoria = util.categoriaDummy();
        var productoRequestDto = util.productoRequestDto();
        when(iCategoriaRepository.existsByNombre(anyString())).thenReturn(true);
        when(iCategoriaRepository.findByNombre(anyString())).thenReturn(Optional.of(categoria));
        var categoriaEsperada = categoriaService.validarCategoria(productoRequestDto);
        Assertions.assertEquals(categoria.getNombre(), categoriaEsperada.getNombre());
    }

    @Test
    void test_debeValidarCategoriaIfInCorrecto(){
        var categoria = util.categoriaDummy();
        var productoRequestDto = util.productoRequestDto();
        when(iCategoriaRepository.existsByNombre(anyString())).thenReturn(false);
        when(iCategoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        var categoriaEsperada = categoriaService.validarCategoria(productoRequestDto);
        Assertions.assertEquals(categoria.getNombre(), categoriaEsperada.getNombre());
    }


}
