package com.arka.products.services.impl;

import com.arka.products.models.dtos.pais.PaisRequestDto;
import com.arka.products.models.dtos.pais.PaisResponseDto;
import com.arka.products.models.entities.Pais;
import com.arka.products.repositories.IPaisRepository;
import com.arka.products.services.IPaisService;
import com.arka.products.utilidades.exceptions.PaisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaisServiceImpl implements IPaisService {

    @Autowired
    private final IPaisRepository iPaisRepository;

    public PaisServiceImpl(IPaisRepository iPaisRepository) {
        this.iPaisRepository = iPaisRepository;
    }

    @Override
    public Optional<PaisResponseDto> guardar(PaisRequestDto paisRequestDto) {
        Pais paisCreado = iPaisRepository.save(new Pais(paisRequestDto.nombre(), paisRequestDto.codigoPais()));
        return Optional.of(new PaisResponseDto(paisCreado.getId(), paisCreado.getNombre(), paisCreado.getCodigo()));
    }

    @Override
    public List<PaisResponseDto> obtenerTodos() {
        return iPaisRepository.findAll().stream()
                .map(pais -> new PaisResponseDto(pais.getId(), pais.getNombre(), pais.getCodigo()))
                .toList();
    }

    @Override
    public Optional<PaisResponseDto> obtenerPaisPorId(Long id) {
        return iPaisRepository.findById(id)
                .map(pais -> new PaisResponseDto(pais.getId(), pais.getNombre(), pais.getCodigo()));
    }

    @Override
    public Optional<PaisResponseDto> actualizar(Long id, PaisRequestDto paisRequestDto) {
        return iPaisRepository.findById(id)
                .map(pais -> {
                    pais.setNombre(paisRequestDto.nombre());
                    pais.setCodigo(paisRequestDto.codigoPais());
                    Pais paisActualizado = iPaisRepository.save(pais);
                    return new PaisResponseDto(paisActualizado.getId(),paisActualizado.getNombre(), paisActualizado.getCodigo());
                });
    }


    @Override
    public void eliminar(Long id) {
        boolean existePais = iPaisRepository.existsById(id);
        if (!existePais) {
            throw new PaisException(
                    "Pais no encontrado con id: " + id,
                    HttpStatus.NOT_FOUND,
                    "PAIS_404"
            );
        }
        iPaisRepository.deleteById(id);
    }

}
