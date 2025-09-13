package com.arka.products.utilidades.mappers;

import com.arka.products.models.dtos.pais.PaisRequestDto;
import com.arka.products.models.dtos.pais.PaisResponseDto;
import com.arka.products.models.entities.Pais;

public class PaisMapper {

    public static Pais dePaisRequestDtoAPais (PaisRequestDto paisRequestDto) {
        if (paisRequestDto == null) {
            return null;
        }
        Pais pais = new Pais();
        pais.setNombre(paisRequestDto.nombre());
        pais.setCodigo(paisRequestDto.codigoPais());
        return pais;
    }

    public static PaisResponseDto dePaisAPaisResponseDto (Pais pais) {
        if (pais == null) {
            return null;
        }
        return new PaisResponseDto(
                pais.getId(),
                pais.getNombre(),
                pais.getCodigo()
        );
    }

    public static Pais dePaisResponseDtoAPais (PaisResponseDto paisResponseDto) {
        if (paisResponseDto == null) {
            return null;
        }
        Pais pais = new Pais();
        pais.setId(paisResponseDto.id());
        pais.setNombre(paisResponseDto.nombre());
        pais.setCodigo(paisResponseDto.codigoPais());
        return pais;
    }
}
