package com.arka.products.services;

import com.arka.products.models.entities.Producto;
import com.arka.products.repositories.IProductoRepository;
import com.arka.products.services.impl.CategoriaServiceImpl;
import com.arka.products.services.impl.MarcaServiceImpl;
import com.arka.products.services.impl.ProductoServiceImpl;
import com.arka.products.utilidades.Utilidades;
import com.arka.products.utilidades.exceptions.ProductoException;
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
public class ProductoServiceImplTest {

    @Mock
    IProductoRepository iProductoRepository;

    @Mock
    CategoriaServiceImpl categoriaService;

    @Mock
    MarcaServiceImpl marcaService;

    @InjectMocks
    ProductoServiceImpl productoService;

    Utilidades util = new Utilidades();
    List<Producto> productos;

    @BeforeEach
    void setUp(){
        productos = new ArrayList<>();
    }

    @Test
    void test_debeGuardarNuevoProducto(){
        var productoRequestDto = util.productoRequestDto();
        var marca = util.marcaDummy();
        var categoria = util.categoriaDummy();
        var producto = util.productoDummy();
        when(iProductoRepository.existsByNombre(anyString())).thenReturn(false);
        when(marcaService.validarMarca(productoRequestDto)).thenReturn(marca);
        when(categoriaService.validarCategoria(productoRequestDto)).thenReturn(categoria);
        when(iProductoRepository.save(any(Producto.class))).thenReturn(producto);
        var productoEsperado = productoService.guardar(productoRequestDto);
        Assertions.assertEquals(producto.getNombre(), productoEsperado.nombre());
    }

    @Test
    void test_debeGenerarExceptionAlGuardarNuevoProducto(){
        when(iProductoRepository.existsByNombre(anyString())).thenReturn(true);
        Assertions.assertThrows(ProductoException.class, () -> {
           productoService.guardar(util.productoRequestDto());
        });
    }

    @Test
    void test_debeObtenerTodosLosProductos(){
        productos = util.listaProductos();
        when(iProductoRepository.findAll()).thenReturn(productos);
        var listaEsperada = productoService.obtenerTodos();
        Assertions.assertEquals(productos.size(), listaEsperada.size());
    }

    @Test
    void test_debeGenerarExceptionAlObtenerTodosLosProductos(){
        when(iProductoRepository.findAll()).thenReturn(productos);
        Assertions.assertThrows(ProductoException.class, () -> {
            productoService.obtenerTodos();
        });
    }

    @Test
    void test_debeObtenerProductoPorId(){
        var producto = util.productoDummy();
        when(iProductoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        var productoEsperado = productoService.obtenerProductoPorId(anyLong());
        Assertions.assertEquals(producto.getNombre(), productoEsperado.nombre());
    }

    @Test
    void test_debeGenerarExceptionAlTratarDeObtenerProductoPorId(){
        when(iProductoRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ProductoException.class, () -> {
            productoService.obtenerProductoPorId(anyLong());
        });
    }

    @Test
    void test_debeActualizarProducto(){
        var productoRequestDto = util.productoRequestDto();
        var marca = util.marcaDummy();
        var categoria = util.categoriaDummy();
        var producto = util.productoDummy();

        when(iProductoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(marcaService.validarMarca(productoRequestDto)).thenReturn(marca);
        when(categoriaService.validarCategoria(productoRequestDto)).thenReturn(categoria);
        when(iProductoRepository.save(any(Producto.class))).thenReturn(producto);
        var productoEsperado = productoService.actualizar(anyLong(), productoRequestDto);
        Assertions.assertEquals(producto.getNombre(), productoEsperado.nombre());
    }

    @Test
    void test_debeGenerarExceptionAlActualizar(){
        when(iProductoRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ProductoException.class, () -> {
            productoService.actualizar(anyLong(), util.productoRequestDto());
        });
    }

    @Test
    void test_debeEliminarProducto(){
        var producto = util.productoDummy();
        when(iProductoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        productoService.eliminar(anyLong());
        Mockito.verify(iProductoRepository, times(1)).delete(producto);
    }

    @Test
    void test_debeGenerarExceptionAlEliminarProducto(){
        when(iProductoRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ProductoException.class, () -> {
            productoService.eliminar(anyLong());
        });
    }

}
