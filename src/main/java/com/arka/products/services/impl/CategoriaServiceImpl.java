package com.arka.products.services.impl;

import com.arka.products.models.dtos.categoria.CategoriaRequestDto;
import com.arka.products.models.dtos.categoria.CategoriaResponseDto;
import com.arka.products.models.dtos.producto.ProductoRequestDto;
import com.arka.products.models.entities.Categoria;
import com.arka.products.models.enums.CodigoError;
import com.arka.products.repositories.ICategoriaRepository;
import com.arka.products.services.ICategoriaService;
import com.arka.products.utilidades.exceptions.CategoriaException;
import com.arka.products.utilidades.mappers.CategoriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements ICategoriaService {

    private final ICategoriaRepository iCategoriaRepository;

    @Override
    @Transactional(rollbackFor = CategoriaException.class)
    public CategoriaResponseDto guardar(CategoriaRequestDto categoriaRequestDto) {

        if (existePorNombre(categoriaRequestDto.nombre())) {
            throw new CategoriaException(
                    "Ya existe una categoria con el nombre: " + categoriaRequestDto.nombre(),
                    HttpStatus.CONFLICT,
                    CodigoError.CONFLICTO.name()
            );
        }
        Categoria categoriaGuardada = iCategoriaRepository.save(new Categoria(categoriaRequestDto.nombre(), categoriaRequestDto.descripcion()));
        return new CategoriaResponseDto(categoriaGuardada.getId(), categoriaGuardada.getNombre(), categoriaGuardada.getDescripcion());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDto> obtenerTodos() {
        var categorias = iCategoriaRepository.findAll();
        if (categorias.isEmpty()) {
            throw new CategoriaException(
                    "Lista de categorias vacia",
                    HttpStatus.NOT_FOUND,
                    CodigoError.NO_ENCONTRADO.name()
            );
        }
        return categorias.stream()
                .map(
                        categoria -> {
                            return new CategoriaResponseDto(
                                    categoria.getId(),
                                    categoria.getNombre(),
                                    categoria.getDescripcion()

                            );
                        }
                )
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDto obtenerCategoriaPorId(Long id) {
        return iCategoriaRepository.findById(id)
                .map(categoria -> {
                    return new CategoriaResponseDto(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
                })
                .orElseThrow(() -> {
                    throw new CategoriaException(
                            "Categoria no econtrada con el id: " + id,
                            HttpStatus.NOT_FOUND,
                            CodigoError.NO_ENCONTRADO.name()
                    );
                });
    }

    @Override
    @Transactional(rollbackFor = CategoriaException.class)
    public CategoriaResponseDto actualizar(Long id, CategoriaRequestDto categoriaRequestDto) {
        return iCategoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNombre(categoriaRequestDto.nombre());
                    categoria.setDescripcion(categoriaRequestDto.descripcion());
                    Categoria categoriaActualizada = iCategoriaRepository.save(categoria);
                    return new CategoriaResponseDto(categoriaActualizada.getId(), categoriaActualizada.getNombre(), categoriaActualizada.getDescripcion());
                })
                .orElseThrow(() -> {
                    throw new CategoriaException(
                            "Error al actualizar categoria: " + categoriaRequestDto.nombre(),
                            HttpStatus.NOT_FOUND,
                            CodigoError.NO_ENCONTRADO.name()
                    );
                });
    }

    @Override
    public void eliminar(Long id) {
        var categoria = iCategoriaRepository.findById(id)
                .orElseThrow(() -> {
                    throw new CategoriaException(
                            "Categoria no encontrada con el id: " + id,
                            HttpStatus.NOT_FOUND,
                            CodigoError.NO_ENCONTRADO.name()
                    );
                });
        iCategoriaRepository.delete(categoria);
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return iCategoriaRepository.existsByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria obtenerPorNombre(String nombre) {
        return iCategoriaRepository.findByNombre(nombre)
                .orElseThrow(() -> new CategoriaException(
                        "No existe la categoría con nombre: " + nombre,
                        HttpStatus.NOT_FOUND,
                        "Obtener Categoria"
                ));
    }

    @Override
    public Categoria validarCategoria(ProductoRequestDto productoRequestDto){
        //Categoria
        if(existePorNombre(productoRequestDto.categoria().nombre())){
            return obtenerPorNombre(productoRequestDto.categoria().nombre());
        }else{
            var categoriaGuardada = this.guardar(productoRequestDto.categoria());
            return CategoriaMapper.deCategoriaResponseDtoACategoria(categoriaGuardada);
        }
    }
}
