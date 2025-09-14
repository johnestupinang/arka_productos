package com.arka.products.services;

import com.arka.products.models.entities.Marca;
import com.arka.products.repositories.IMarcaRepository;
import com.arka.products.services.impl.MarcaServiceImpl;
import com.arka.products.services.impl.PaisServiceImpl;
import com.arka.products.utilidades.Utilidades;
import com.arka.products.utilidades.exceptions.MarcaException;
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
public class MarcaServiceTest {

    @Mock
    IMarcaRepository iMarcaRepository;

    @Mock
    PaisServiceImpl paisService;

    @InjectMocks
    MarcaServiceImpl marcaService;

    Utilidades util = new Utilidades();
    List<Marca> marcas;

    @BeforeEach
    void setUp(){
        marcas = new ArrayList<>();
    }

    @Test
    void test_debeGuardarMarca(){
        var marca = util.marcaDummy();
        var marcaRequestDto = util.marcaRequestDto();
        when(iMarcaRepository.existsByNombre(anyString())).thenReturn(false);
        when(iMarcaRepository.save(any(Marca.class))).thenReturn(marca);
        var marcaEsperada = marcaService.guardar(marcaRequestDto);
        Assertions.assertEquals(marca.getNombre(), marcaEsperada.nombre());
    }

    @Test
    void test_debeGenerarExceptionAlTratarDeGuardar(){
        when(iMarcaRepository.existsByNombre(anyString())).thenReturn(true);
        Assertions.assertThrows(MarcaException.class, () -> {
            marcaService.guardar(util.marcaRequestDto());
        });
    }

    @Test
    void test_debeObtenerTodasLasMarcas(){
        marcas = util.listaMarcas();
        when(iMarcaRepository.findAll()).thenReturn(marcas);
        var listaMarcasEsperada = marcaService.obtenerTodos();
        Assertions.assertEquals(marcas.size(), listaMarcasEsperada.size());
    }

    @Test
    void test_debeGenerarExceptionPAraObtenerTodasLasMarcas(){
        when(iMarcaRepository.findAll()).thenReturn(marcas);
        Assertions.assertThrows(MarcaException.class, () -> {
           marcaService.obtenerTodos();
        });
    }

    @Test
    void test_debeObtenerMarcaPorId(){
        var marca = util.marcaDummy();
        when(iMarcaRepository.findById(anyLong())).thenReturn(Optional.of(marca));
        var marcaEsperada = marcaService.obtenerMarcaPorId(anyLong());
        Assertions.assertEquals(marca.getNombre(), marcaEsperada.nombre());
    }

    @Test
    void test_debeGenerarErrorAlTratarDeObtenerMarcaPorId(){
        when(iMarcaRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(MarcaException.class, () -> {
            marcaService.obtenerMarcaPorId(anyLong());
        });
    }

    @Test
    void test_debeActualizarMarca(){
        var marca = util.marcaDummy();
        var marcaRequestDto = util.marcaRequestDto();
        var pais = util.paisDummy();
        when(iMarcaRepository.findById(anyLong())).thenReturn(Optional.of(marca));
        when(paisService.validarPais(marcaRequestDto)).thenReturn(pais);
        when(iMarcaRepository.save(any(Marca.class))).thenReturn(marca);
        var marcaEsperada = marcaService.actualizar(anyLong(), marcaRequestDto);
        Assertions.assertEquals(marca.getNombre(), marcaEsperada.nombre());
    }

    @Test
    void test_debeGenerarExceptionAlActualizarMarca(){
        when(iMarcaRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(MarcaException.class, () -> {
           marcaService.actualizar(anyLong(), util.marcaRequestDto());
        });
    }

    @Test
    void test_debeEliminarMarca(){
        var marca = util.marcaDummy();
        when(iMarcaRepository.findById(anyLong())).thenReturn(Optional.of(marca));
        marcaService.eliminar(anyLong());
        Mockito.verify(iMarcaRepository, times(1)).delete(marca);
    }

    @Test
    void test_debeGenerarExceptionAlEliminarMarca(){
        when(iMarcaRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(MarcaException.class, ()-> {
           marcaService.eliminar(anyLong());
        });
    }

    @Test
    void test_debeObtenerMarcaPorNombre(){
        var marca = util.marcaDummy();
        when(iMarcaRepository.findByNombre(anyString())).thenReturn(Optional.of(marca));
        var marcaEsperada = marcaService.obtenerPorNombre(anyString());
        Assertions.assertEquals(marca.getNombre(), marcaEsperada.getNombre());
    }

    @Test
    void test_debeGenerarExceptionAlObtenerMarcaPorNombre(){
        when(iMarcaRepository.findByNombre(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(MarcaException.class, () -> {
           marcaService.obtenerPorNombre(anyString());
        });
    }

    @Test
    void test_debeValidarMarcaSiExisteMarca(){
        var productoRequestDto = util.productoRequestDto();
        var marca = util.marcaDummy();
        when(iMarcaRepository.existsByNombre(anyString())).thenReturn(true);
        when(iMarcaRepository.findByNombre(anyString())).thenReturn(Optional.of(marca));
        var marcaEsperada = marcaService.validarMarca(productoRequestDto);
        Assertions.assertEquals(marca.getNombre(), marcaEsperada.getNombre());
    }

    @Test
    void test_debeValidarMarcaRegistraNuevaMarca(){
        var marca = util.marcaDummy();
        var pais = util.paisDummy();
        var marcaRequestDto = util.marcaRequestDto();
        var productoRequestDto = util.productoRequestDto();
        when(iMarcaRepository.existsByNombre(anyString())).thenReturn(false);
        when(paisService.validarPais(marcaRequestDto)).thenReturn(pais);
        when(iMarcaRepository.save(any(Marca.class))).thenReturn(marca);
        var marcaEsperada = marcaService.validarMarca(productoRequestDto);
        Assertions.assertEquals(marca.getNombre(), marcaEsperada.getNombre());
    }
}
