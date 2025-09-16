package com.arka.products.utilidades.mappers;

import com.arka.products.models.dtos.proveedor.ProveedorRequestDto;
import com.arka.products.models.dtos.proveedor.ProveedorResponseDto;
import com.arka.products.models.entities.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {

    private static PaisMapper paisMapper = new PaisMapper();

    public static ProveedorResponseDto deProveedorAProveedorResponseDto(Proveedor proveedor){
        if(proveedor == null){
            return null;
        }
        return new ProveedorResponseDto(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getEmail(),
                proveedor.getTelefono(),
                proveedor.getDireccion(),
                paisMapper.dePaisAPaisResponseDto(proveedor.getPais())
        );
    }

    public static Proveedor deProveedorRequestDtoAProveedor(ProveedorRequestDto proveedorRequestDto){
        if(proveedorRequestDto == null){
            return null;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(proveedorRequestDto.nombre());
        proveedor.setEmail(proveedorRequestDto.email());
        proveedor.setTelefono(proveedorRequestDto.telefono());
        proveedor.setDireccion(proveedorRequestDto.direccion());
        proveedor.setPais(paisMapper.dePaisRequestDtoAPais(proveedorRequestDto.pais()));
        return proveedor;
    }
}
