package com.arka.products.services.impl;

import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.marca.MarcaResponseDto;
import com.arka.products.models.dtos.pais.PaisResponseDto;
import com.arka.products.models.dtos.producto.ProductoRequestDto;
import com.arka.products.models.entities.Marca;
import com.arka.products.models.enums.CodigoError;
import com.arka.products.repositories.IMarcaRepository;
import com.arka.products.services.IMarcaService;
import com.arka.products.services.IPaisService;
import com.arka.products.utilidades.exceptions.MarcaException;
import com.arka.products.utilidades.exceptions.PaisException;
import com.arka.products.utilidades.mappers.MarcaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements IMarcaService {

    private final IMarcaRepository iMarcaRepository;
    private final IPaisService paisService;

    @Override
    @Transactional(rollbackFor = MarcaException.class)
    public MarcaResponseDto guardar(MarcaRequestDto marcaRequestDto) {
        if(existePorNombre(marcaRequestDto.nombre())){
            throw new MarcaException(
                    "Ya existe una marca con el nombre: "+marcaRequestDto.nombre(),
                    HttpStatus.CONFLICT,
                    CodigoError.CONFLICTO.name()
            );
        }
        Marca marcaCreada = iMarcaRepository.save(new Marca(marcaRequestDto.nombre(), marcaRequestDto.descripcion(), paisService.validarPais(marcaRequestDto)));
        return new MarcaResponseDto(marcaCreada.getId(), marcaCreada.getNombre(), marcaCreada.getDescripcion(), new PaisResponseDto(marcaCreada.getPais().getId(), marcaCreada.getPais().getNombre(), marcaCreada.getPais().getCodigo()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MarcaResponseDto> obtenerTodos() {
        var marcas = iMarcaRepository.findAll();
        if(marcas.isEmpty()){
            throw new MarcaException(
                    "Lista de marcas vacia",
                    HttpStatus.NOT_FOUND,
                    CodigoError.NO_ENCONTRADO.name()
            );
        }
        return marcas.stream()
                .map(marca -> new MarcaResponseDto(marca.getId(), marca.getNombre(), marca.getDescripcion(), new PaisResponseDto(marca.getPais().getId(), marca.getPais().getNombre(), marca.getPais().getCodigo())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MarcaResponseDto obtenerMarcaPorId(Long id) {
        return iMarcaRepository.findById(id)
                .map( marca -> new MarcaResponseDto(marca.getId(), marca.getNombre(), marca.getDescripcion(), new PaisResponseDto(marca.getPais().getId(), marca.getPais().getNombre(), marca.getPais().getCodigo())))
                .orElseThrow(
                        () -> {
                            throw new MarcaException("Marca no encontrada con el id: "+ id, HttpStatus.NOT_FOUND, CodigoError.NO_ENCONTRADO.name());
                        }
                );
    }

    @Override
    @Transactional(rollbackFor = PaisException.class)
    public MarcaResponseDto actualizar(Long id, MarcaRequestDto marcaRequestDto) {
        return iMarcaRepository.findById(id)
                .map(
                        marca -> {

                            marca.setNombre(marcaRequestDto.nombre());
                            marca.setDescripcion(marcaRequestDto.descripcion());
                            marca.setPais(paisService.validarPais(marcaRequestDto));

                            Marca marcaActualizada = iMarcaRepository.save(marca);
                            return new MarcaResponseDto(marcaActualizada.getId(), marcaActualizada.getNombre(), marcaActualizada.getDescripcion(), new PaisResponseDto(marcaActualizada.getPais().getId(), marcaActualizada.getPais().getNombre(), marcaActualizada.getPais().getCodigo()));
                        }
                )
                .orElseThrow(
                        () -> {
                            throw new MarcaException(
                              "Error al actualizar marca: "+marcaRequestDto.nombre(),
                              HttpStatus.NOT_FOUND,
                              CodigoError.NO_ENCONTRADO.name()
                            );
                        }
                );
    }

    @Override
    public void eliminar(Long id) {
        var marca = iMarcaRepository.findById(id)
                .orElseThrow(() -> {
                    throw new MarcaException(
                      "Marca no encontrada con el id: "+id,
                      HttpStatus.NOT_FOUND,
                      CodigoError.NO_ENCONTRADO.name()
                    );
                });
        iMarcaRepository.delete(marca);
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return iMarcaRepository.existsByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Marca obtenerPorNombre(String nombre) {
        return iMarcaRepository.findByNombre(nombre).orElseThrow(
                () -> new MarcaException(
                        "Marca no encontrada con nombre: " + nombre,
                        HttpStatus.NOT_FOUND,
                        CodigoError.NO_ENCONTRADO.name()
                )
        );
    }

    @Override
    public Marca validarMarca(ProductoRequestDto productoRequestDto){
        //Marca
        if (existePorNombre(productoRequestDto.marca().nombre())){
            return obtenerPorNombre(productoRequestDto.marca().nombre());
        }else{
            var marcaGuardada = guardar(productoRequestDto.marca());
            return MarcaMapper.deMarcaResponseDtoAMarca(marcaGuardada);
        }
    }
}
