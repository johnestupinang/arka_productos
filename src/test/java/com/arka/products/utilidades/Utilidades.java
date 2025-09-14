package com.arka.products.utilidades;

import com.arka.products.models.dtos.categoria.CategoriaRequestDto;
import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.pais.PaisRequestDto;
import com.arka.products.models.dtos.producto.ProductoRequestDto;
import com.arka.products.models.entities.Categoria;
import com.arka.products.models.entities.Marca;
import com.arka.products.models.entities.Pais;
import com.arka.products.models.entities.Producto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Utilidades {

    private static final List<Pais> paises = new ArrayList<>();
    private static final List<Categoria> categorias = new ArrayList<>();
    private static final List<Marca> marcas = new ArrayList<>();

    public Pais paisDummy(){
        return new Pais(1L, "Colombia", "COL");
    }

    public Categoria categoriaDummy(){
        return new Categoria(1L, "Alimentos", "Comestibles");
    }

    public Marca marcaDummy(){
        return new Marca(1L, "Colombina", "Comestibles", paisDummy());
    }

    public Producto productoDummy(){
        return new Producto(1L, "Helado", "Helado de vainilla", 1500.00, 5, 30, LocalDateTime.now(), LocalDateTime.now(), categoriaDummy(), marcaDummy());
    }

    public PaisRequestDto paisRequestDtoDummy() {
        Pais pais = paisDummy();
        return new PaisRequestDto(
                pais.getNombre(),
                pais.getCodigo()
        );
    }

    public CategoriaRequestDto categoriaRequestDtoDummy() {
        Categoria categoria = categoriaDummy();
        return new CategoriaRequestDto(
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }

    public MarcaRequestDto marcaRequestDto(){
        Marca marca = marcaDummy();
        return new MarcaRequestDto(marca.getNombre(), marca.getDescripcion(), paisRequestDtoDummy());
    }

    public ProductoRequestDto productoRequestDto() {
        Producto producto = productoDummy();
        return new ProductoRequestDto(
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadMinima(),
                producto.getCantidad(),
                categoriaRequestDtoDummy(),
                marcaRequestDto()
        );
    }

    public List<Pais> listaPaises(){
        paises.add(paisDummy());
        return paises;
    }

    public List<Categoria> listaCategorias(){
        categorias.add(categoriaDummy());
        return categorias;
    }

    public List<Marca> listaMarcas(){
        marcas.add(marcaDummy());
        return marcas;
    }
}
