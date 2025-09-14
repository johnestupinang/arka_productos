package com.arka.products.services;

import com.arka.products.models.entities.Pais;
import com.arka.products.repositories.IPaisRepository;
import com.arka.products.services.impl.PaisServiceImpl;
import com.arka.products.utilidades.Utilidades;
import com.arka.products.utilidades.exceptions.PaisException;
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
public class PaisServiceTest {

    Utilidades util = new Utilidades();
    List<Pais> paises;

    @BeforeEach
    void setUp(){
        paises = new ArrayList<>();
    }

    @Mock
    IPaisRepository iPaisRepository;

    @InjectMocks
    PaisServiceImpl paisService;

    @Test
    void test_debeGuardarPais_Exitoso(){
        Pais pais = util.paisDummy();
        when(iPaisRepository.existsByNombre(anyString())).thenReturn(false);
        when(iPaisRepository.save(any(Pais.class))).thenReturn(pais);
        var paisEsperado = paisService.guardar(util.paisRequestDtoDummy());
        Assertions.assertEquals(pais.getNombre(), paisEsperado.nombre());
    }

    @Test
    void test_debeGenerarExcepcionAlGuardarPaid_Falla(){
        when(iPaisRepository.existsByNombre(anyString())).thenReturn(true);
        Assertions.assertThrows(
                PaisException.class, () -> {
                   paisService.guardar(util.paisRequestDtoDummy());
                }
        );
    }

    @Test
    void test_debeObtenerListaDePaises_Exitosa(){
        paises = util.listaPaises();
        when(iPaisRepository.findAll()).thenReturn(paises);
        var listaEsperada = paisService.obtenerTodos();
        Assertions.assertEquals(listaEsperada.size(), paises.size());
    }

    @Test
    void test_debeGenerarException(){
        when(iPaisRepository.findAll()).thenReturn(paises);
        Assertions.assertThrows(PaisException.class, () -> {
            paisService.obtenerTodos();
        });
    }

    @Test
    void test_debeObtenerPaisPorId_Exitoso(){
        when(iPaisRepository.findById(anyLong())).thenReturn(Optional.of(util.paisDummy()));
        var paisEsperado = paisService.obtenerPaisPorId(anyLong());
        Assertions.assertEquals(util.paisRequestDtoDummy().nombre(), paisEsperado.nombre());
    }

    @Test
    void test_debeGenerarExceptionAlConsultarPorId_Fallido(){
        when(iPaisRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(PaisException.class, () -> {
           paisService.obtenerPaisPorId(anyLong());
        });
    }

    @Test
    void test_debeActualizarPais_Exitoso(){
        var pais = util.paisDummy();
        when(iPaisRepository.findById(anyLong())).thenReturn(Optional.of(pais));
        when(iPaisRepository.save(any(Pais.class))).thenReturn(pais);
        var paisActualizadoEsperado = paisService.actualizar(anyLong(), util.paisRequestDtoDummy());
        Assertions.assertEquals(pais.getNombre(), paisActualizadoEsperado.nombre());
    }

    @Test
    void test_debeGenerarExceptionAlActualizarPorId_Fallido(){
        when(iPaisRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(PaisException.class, () -> {
            paisService.actualizar(anyLong(), util.paisRequestDtoDummy());
        });
    }

    @Test
    void test_debeEliminarPaisPorId_Exitoso(){
        var pais = util.paisDummy();
        when(iPaisRepository.findById(anyLong())).thenReturn(Optional.of(pais));
        paisService.eliminar(anyLong());
        Mockito.verify(iPaisRepository, times(1)).delete(pais);
    }

    @Test
    void test_debeGenerarExceptionAlEliminarPais(){
        when(iPaisRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(PaisException.class, () -> {
           paisService.eliminar(anyLong());
        });
    }

    @Test
    void test_debeValidarPaisIfCorrecto(){
        var marcaRequestDto = util.marcaRequestDto();
        var pais = util.paisDummy();
        when(iPaisRepository.existsByNombre(anyString())).thenReturn(true);
        when(iPaisRepository.findByNombre(anyString())).thenReturn(pais);
        var paisEsperado = paisService.validarPais(marcaRequestDto);
        Assertions.assertEquals(pais.getNombre(), paisEsperado.getNombre());
    }

    @Test
    void test_debeValidarPaidElse(){
        var marcaRequestDto = util.marcaRequestDto();
        var pais = util.paisDummy();
        when(iPaisRepository.existsByNombre(anyString())).thenReturn(false);
        when(iPaisRepository.save(any(Pais.class))).thenReturn(pais);
        var paisEsperado = paisService.validarPais(marcaRequestDto);
        Assertions.assertEquals(pais.getNombre(), paisEsperado.getNombre());
    }
}
