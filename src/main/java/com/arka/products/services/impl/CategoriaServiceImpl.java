package com.arka.products.services.impl;

import com.arka.products.models.dtos.categoria.CategoriaRequestDto;
import com.arka.products.models.dtos.categoria.CategoriaResponseDto;
import com.arka.products.models.entities.Categoria;
import com.arka.products.repositories.ICategoriaRepository;
import com.arka.products.services.ICategoriaService;
import com.arka.products.utilidades.exceptions.CategoriaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private final ICategoriaRepository iCategoriaRepository;

    public CategoriaServiceImpl(ICategoriaRepository iCategoriaRepository) {
        this.iCategoriaRepository = iCategoriaRepository;
    }

    @Override
    public Optional<CategoriaResponseDto> guardar(CategoriaRequestDto categoriaRequestDto) {

        if (iCategoriaRepository.existsByNombre(categoriaRequestDto.nombre())) {
            return Optional.empty();
        }

        Categoria categoriaGuardada = iCategoriaRepository.save(new Categoria(categoriaRequestDto.nombre(), categoriaRequestDto.descripcion()));
        return Optional.of(new CategoriaResponseDto(categoriaGuardada.getId(), categoriaGuardada.getNombre(), categoriaGuardada.getDescripcion()));
    }

    @Override
    public List<CategoriaResponseDto> obtenerTodos() {
        return iCategoriaRepository.findAll()
                .stream()
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
    public Optional<CategoriaResponseDto> obtenerCategoriaPorId(Long id) {
        return iCategoriaRepository.findById(id)
                .map(categoria -> {
                    return new CategoriaResponseDto(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
                });
    }

    @Override
    public Optional<CategoriaResponseDto> actualizar(Long id, CategoriaRequestDto categoriaRequestDto) {
        return iCategoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNombre(categoriaRequestDto.nombre());
                    categoria.setDescripcion(categoriaRequestDto.descripcion());
                    Categoria categoriaActualizada = iCategoriaRepository.save(categoria);
                    return new CategoriaResponseDto(categoriaActualizada.getId(), categoriaActualizada.getNombre(), categoriaActualizada.getDescripcion());
                });
    }

    @Override
    public void eliminar(Long id) {

        if(!iCategoriaRepository.existsById(id)){
            throw new CategoriaException(
                    "No existe la categoria con id: " + id,
                    HttpStatus.NOT_FOUND,
                    "Eliminar Categoria"
            );
        }else{
            iCategoriaRepository.deleteById(id);
        }

    }
}
