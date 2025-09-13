package com.arka.products.services.impl;

import com.arka.products.models.dtos.producto.ProductoRequestDto;
import com.arka.products.models.dtos.producto.ProductoResponseDto;
import com.arka.products.models.enums.CodigoError;
import com.arka.products.repositories.IProductoRepository;
import com.arka.products.services.ICategoriaService;
import com.arka.products.services.IMarcaService;
import com.arka.products.services.IProductoService;
import com.arka.products.utilidades.exceptions.ProductoException;
import com.arka.products.utilidades.mappers.ProductoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    private final IProductoRepository iProductoRepository;
    private final ICategoriaService categoriaService;
    private final IMarcaService marcaService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductoResponseDto guardar(ProductoRequestDto productoRequestDto) {
        if(existsProductoByNombre(productoRequestDto)){
            throw new ProductoException(
                    "Ya existe un producto con el nombre: "+ productoRequestDto.nombre(),
                    HttpStatus.CONFLICT,
                    CodigoError.CONFLICTO.name()
            );
        }

        var marca = marcaService.validarMarca(productoRequestDto);
        var categoria = categoriaService.validarCategoria(productoRequestDto);

        var producto = ProductoMapper.deProductoRequestDtoAProducto(productoRequestDto);
        producto.setFechaCreacion(LocalDateTime.now());
        producto.setFechaActualizacion(LocalDateTime.now());
        producto.setMarca(marca);
        producto.setCategoria(categoria);

        var productoCreado = iProductoRepository.save(producto);

        return ProductoMapper.deProductoAProductoResponseDto(productoCreado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDto> obtenerTodos() {
        var listaProductos = iProductoRepository.findAll();
        if (listaProductos.isEmpty()){
            throw new ProductoException(
                    "Lista de productos vacia",
                    HttpStatus.NOT_FOUND,
                    CodigoError.NO_ENCONTRADO.name()
            );
        }
        return listaProductos.stream()
                .map(producto -> {
                     return ProductoMapper.deProductoAProductoResponseDto(producto);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDto obtenerProductoPorId(Long id) {
        return iProductoRepository.findById(id)
                .map(producto -> {
                    return ProductoMapper.deProductoAProductoResponseDto(producto);
                })
                .orElseThrow(
                        () -> {
                            throw new ProductoException(
                                    "Producto no encontrado con id: " + id,
                                    HttpStatus.NOT_FOUND,
                                    CodigoError.NO_ENCONTRADO.name()
                            );
                        }
                );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductoResponseDto actualizar(Long id, ProductoRequestDto productoRequestDto) {
        return iProductoRepository.findById(id)
                    .map(producto -> {

                        producto.setNombre(productoRequestDto.nombre());
                        producto.setDescripcion(productoRequestDto.descripcion());
                        producto.setPrecio(productoRequestDto.precio());
                        producto.setCantidadMinima(productoRequestDto.cantidadMinima());
                        producto.setCantidad(productoRequestDto.cantidad());
                        producto.setFechaActualizacion(LocalDateTime.now());
                        //Categoria
                        producto.setCategoria(categoriaService.validarCategoria(productoRequestDto));
                        //Marca
                        producto.setMarca(marcaService.validarMarca(productoRequestDto));
                        return ProductoMapper.deProductoAProductoResponseDto(iProductoRepository.save(producto));

                    })
                .orElseThrow(() -> {
                    throw new ProductoException(
                            "Error al actualizar producto: "+ productoRequestDto.nombre(),
                            HttpStatus.CONFLICT,
                            CodigoError.CONFLICTO.name()
                    );
                });
    }

    @Override
    public void eliminar(Long id) {
        var producto = iProductoRepository.findById(id)
                .orElseThrow(() -> new ProductoException(
                        "Producto no encontrado con id: " + id,
                        HttpStatus.NOT_FOUND,
                        CodigoError.NO_ENCONTRADO.name()
                ));
        iProductoRepository.delete(producto);
    }

    @Override
    public boolean existsProductoByNombre(ProductoRequestDto productoRequestDto) {
        return iProductoRepository.existsByNombre(productoRequestDto.nombre());
    }

}
