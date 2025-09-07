package com.arka.products.services.impl;

import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.marca.MarcaResponseDto;
import com.arka.products.models.dtos.pais.PaisResponseDto;
import com.arka.products.models.entities.Marca;
import com.arka.products.models.entities.Pais;
import com.arka.products.repositories.IMarcaRepository;
import com.arka.products.repositories.IPaisRepository;
import com.arka.products.services.IMarcaService;
import com.arka.products.services.IPaisService;
import com.arka.products.utilidades.exceptions.MarcaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaServiceImpl implements IMarcaService {

    @Autowired
    private final IMarcaRepository iMarcaRepository;
    @Autowired
    private final IPaisRepository iPaisRepository;
    @Autowired
    private final IPaisService iPaisService;


    public MarcaServiceImpl(IMarcaRepository iMarcaRepository, IPaisRepository iPaisRepository, IPaisService iPaisService) {
        this.iPaisRepository = iPaisRepository;
        this.iMarcaRepository = iMarcaRepository;
        this.iPaisService = iPaisService;
    }


    @Override
    public Optional<MarcaResponseDto> guardar(MarcaRequestDto marcaRequestDto) {

        if(iMarcaRepository.existsByNombre(marcaRequestDto.nombre())){
            return Optional.empty();
        }

        Marca marcaCreada = iMarcaRepository.save(new Marca(marcaRequestDto.nombre(), marcaRequestDto.descripcion(), validarPais(marcaRequestDto)));
        return Optional.of(new MarcaResponseDto(marcaCreada.getId(), marcaCreada.getNombre(), marcaCreada.getDescripcion(), new PaisResponseDto(marcaCreada.getPais().getId(), marcaCreada.getPais().getNombre(), marcaCreada.getPais().getCodigo())));

    }

    @Override
    public List<MarcaResponseDto> obtenerTodos() {
        return iMarcaRepository.findAll().stream()
                .map(marca -> new MarcaResponseDto(marca.getId(), marca.getNombre(), marca.getDescripcion(), new PaisResponseDto(marca.getPais().getId(), marca.getPais().getNombre(), marca.getPais().getCodigo())))
                .toList();
    }

    @Override
    public Optional<MarcaResponseDto> obtenerMarcaPorId(Long id) {
        return iMarcaRepository.findById(id)
                .map( marca -> new MarcaResponseDto(marca.getId(), marca.getNombre(), marca.getDescripcion(), new PaisResponseDto(marca.getPais().getId(), marca.getPais().getNombre(), marca.getPais().getCodigo())));
    }

    @Override
    public Optional<MarcaResponseDto> actualizar(Long id, MarcaRequestDto marcaRequestDto) {
        return iMarcaRepository.findById(id)
                .map(
                        marca -> {

                            marca.setNombre(marcaRequestDto.nombre());
                            marca.setDescripcion(marcaRequestDto.descripcion());
                            marca.setPais(validarPais(marcaRequestDto));

                            Marca marcaActualizada = iMarcaRepository.save(marca);
                            return new MarcaResponseDto(marcaActualizada.getId(), marcaActualizada.getNombre(), marcaActualizada.getDescripcion(), new PaisResponseDto(marcaActualizada.getPais().getId(), marcaActualizada.getPais().getNombre(), marcaActualizada.getPais().getCodigo()));
                        }
                );
    }

    @Override
    public void eliminar(Long id) {
        if (!iMarcaRepository.existsById(id)) {
            throw new MarcaException(
                    "Marca no encontrada con id: " + id,
                    HttpStatus.NOT_FOUND,
                    "MARCA_404"
            );
        }
        iMarcaRepository.deleteById(id);
    }

    private Pais validarPais(MarcaRequestDto marcaRequestDto){

        if(iPaisRepository.existsByNombre(marcaRequestDto.pais().nombre())){
            return iPaisRepository.findByNombre(marcaRequestDto.pais().nombre());
        }else{
            return iPaisRepository.save(new Pais(marcaRequestDto.pais().nombre(), marcaRequestDto.pais().codigoPais()));
        }

    }
}
