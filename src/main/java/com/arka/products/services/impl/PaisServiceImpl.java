package com.arka.products.services.impl;

import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.pais.PaisRequestDto;
import com.arka.products.models.dtos.pais.PaisResponseDto;
import com.arka.products.models.entities.Pais;
import com.arka.products.models.enums.CodigoError;
import com.arka.products.repositories.IPaisRepository;
import com.arka.products.services.IPaisService;
import com.arka.products.utilidades.exceptions.PaisException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaisServiceImpl implements IPaisService {

    private final IPaisRepository iPaisRepository;

    @Override
    @Transactional(rollbackFor = PaisException.class)
    public PaisResponseDto guardar(PaisRequestDto paisRequestDto) {
        if(iPaisRepository.existsByNombre(paisRequestDto.nombre())){
            throw new PaisException(
                    "Ya existe un pais con el nombre: "+paisRequestDto.nombre(),
                    HttpStatus.CONFLICT,
                    CodigoError.CONFLICTO.name()
            );
        }
        Pais paisCreado = iPaisRepository.save(new Pais(paisRequestDto.nombre(), paisRequestDto.codigoPais()));
        return new PaisResponseDto(paisCreado.getId(), paisCreado.getNombre(), paisCreado.getCodigo());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaisResponseDto> obtenerTodos() {
        var paises = iPaisRepository.findAll();
        if(paises.isEmpty()){
            throw new PaisException(
              "Lista de paises vacia",
              HttpStatus.NOT_FOUND,
              CodigoError.NO_ENCONTRADO.name()
            );
        }

        return paises.stream()
                .map(pais -> new PaisResponseDto(pais.getId(), pais.getNombre(), pais.getCodigo()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PaisResponseDto obtenerPaisPorId(Long id) {
        return iPaisRepository.findById(id)
                .map(pais -> new PaisResponseDto(pais.getId(), pais.getNombre(), pais.getCodigo()))
                .orElseThrow(
                        () -> {
                            throw new PaisException(
                              "Pais no encontrado con el id: "+ id,
                              HttpStatus.NOT_FOUND,
                              CodigoError.NO_ENCONTRADO.name()
                            );
                        }
                );
    }

    @Override
    @Transactional(rollbackFor = PaisException.class)
    public PaisResponseDto actualizar(Long id, PaisRequestDto paisRequestDto) {
        return iPaisRepository.findById(id)
                .map(pais -> {
                    pais.setNombre(paisRequestDto.nombre());
                    pais.setCodigo(paisRequestDto.codigoPais());
                    Pais paisActualizado = iPaisRepository.save(pais);
                    return new PaisResponseDto(paisActualizado.getId(),paisActualizado.getNombre(), paisActualizado.getCodigo());
                })
                .orElseThrow(
                        () -> {
                            throw new PaisException(
                              "Error al actualizar pais: "+ paisRequestDto.nombre(),
                              HttpStatus.CONFLICT,
                              CodigoError.CONFLICTO.name()
                            );
                        }
                );
    }

    @Override
    public void eliminar(Long id) {
        var pais = iPaisRepository.findById(id)
                .orElseThrow(
                        () -> {
                            throw new PaisException(
                                    "Producto no encontrado con id: "+id,
                                    HttpStatus.NOT_FOUND,
                                    CodigoError.NO_ENCONTRADO.name()
                            );
                        }
                );

        iPaisRepository.delete(pais);
    }

    public Pais validarPais(MarcaRequestDto marcaRequestDto){
        if(iPaisRepository.existsByNombre(marcaRequestDto.pais().nombre())){
            return iPaisRepository.findByNombre(marcaRequestDto.pais().nombre());
        }else{
            return iPaisRepository.save(new Pais(marcaRequestDto.pais().nombre(), marcaRequestDto.pais().codigoPais()));
        }
    }
}
