package com.arka.products.utilidades.mappers;

import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.marca.MarcaResponseDto;
import com.arka.products.models.entities.Marca;

public class MarcaMapper {

    public static Marca deMarcaRequestDtoAMarca (MarcaRequestDto marcaRequestDto) {
        if (marcaRequestDto == null) {
            return null;
        }
        Marca marca = new Marca();
        marca.setNombre(marcaRequestDto.nombre());
        marca.setDescripcion(marcaRequestDto.descripcion());
        marca.setPais(PaisMapper.dePaisRequestDtoAPais(marcaRequestDto.pais()));
        return marca;
    }

    public static MarcaResponseDto deMarcaAMarcaResponseDto (Marca marca) {
        if (marca == null) {
            return null;
        }
        return new MarcaResponseDto(
                marca.getId(),
                marca.getNombre(),
                marca.getDescripcion(),
                PaisMapper.dePaisAPaisResponseDto(marca.getPais())
        );
    }

    public static Marca deMarcaResponseDtoAMarca (MarcaResponseDto marcaResponseDto) {
        if (marcaResponseDto == null) {
            return null;
        }
        Marca marca = new Marca();
        marca.setId(marcaResponseDto.id());
        marca.setNombre(marcaResponseDto.nombre());
        marca.setDescripcion(marcaResponseDto.descripcion());
        marca.setPais(PaisMapper.dePaisResponseDtoAPais(marcaResponseDto.pais()));
        return marca;
    }
}
