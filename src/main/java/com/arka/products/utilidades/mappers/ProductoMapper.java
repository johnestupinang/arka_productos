package com.arka.products.utilidades.mappers;

import com.arka.products.models.dtos.producto.ProductoRequestDto;
import com.arka.products.models.dtos.producto.ProductoResponseDto;
import com.arka.products.models.entities.Producto;

public class ProductoMapper {

    public static Producto deProductoRequestDtoAProducto(ProductoRequestDto productoRequestDto) {
        if (productoRequestDto == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setNombre(productoRequestDto.nombre());
        producto.setDescripcion(productoRequestDto.descripcion());
        producto.setPrecio(productoRequestDto.precio());
        producto.setCantidadMinima(productoRequestDto.cantidadMinima());
        producto.setCantidad(productoRequestDto.cantidad());
        producto.setCategoria(CategoriaMapper.deCategoriaRequestDtoACategoria(productoRequestDto.categoria()));
        producto.setMarca(MarcaMapper.deMarcaRequestDtoAMarca(productoRequestDto.marca()));
        return producto;
    }

    public static ProductoResponseDto deProductoAProductoResponseDto(Producto producto) {
        if (producto == null) {
            return null;
        }
        return new ProductoResponseDto(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadMinima(),
                producto.getCantidad(),
                producto.getFechaCreacion(),
                producto.getFechaActualizacion(),
                CategoriaMapper.deCategoriaACategoriaResponseDto(producto.getCategoria()),
                MarcaMapper.deMarcaAMarcaResponseDto(producto.getMarca())
        );
    }

}
