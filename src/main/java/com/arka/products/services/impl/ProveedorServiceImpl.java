package com.arka.products.services.impl;

import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.proveedor.ProveedorRequestDto;
import com.arka.products.models.dtos.proveedor.ProveedorResponseDto;
import com.arka.products.models.entities.Pais;
import com.arka.products.models.entities.Proveedor;
import com.arka.products.models.enums.CodigoError;
import com.arka.products.repositories.IProveedorRepository;
import com.arka.products.services.IPaisService;
import com.arka.products.services.IProveedorService;
import com.arka.products.utilidades.exceptions.ProveedorException;
import com.arka.products.utilidades.mappers.ProveedorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements IProveedorService {

    private final IProveedorRepository iProveedorRepository;
    private final ProveedorMapper proveedorMapper;
    private final IPaisService paisService;

    @Override
    @Transactional(rollbackFor = ProveedorException.class)
    public ProveedorResponseDto guardar(ProveedorRequestDto proveedorRequestDto) {
        if(iProveedorRepository.existsProveedorByNombre(proveedorRequestDto.nombre())){
            throw new ProveedorException("Ya existe un proveedor con el nombre: "+ proveedorRequestDto.nombre(),
                    HttpStatus.CONFLICT,
                    CodigoError.CONFLICTO.name());
        }
        Pais pais = paisService.validarPais(new MarcaRequestDto("", "", proveedorRequestDto.pais()));
        Proveedor proveedorAGuardar = proveedorMapper.deProveedorRequestDtoAProveedor(proveedorRequestDto);
        proveedorAGuardar.setPais(pais);
        Proveedor proveedorCreado = iProveedorRepository.save(proveedorAGuardar);
        return proveedorMapper.deProveedorAProveedorResponseDto(proveedorCreado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorResponseDto> obtenerTodos() {
        var paises = iProveedorRepository.findAll();
        if(paises.isEmpty()){
            throw new ProveedorException(
                    "Lista de proveedores vacia",
                    HttpStatus.NOT_FOUND,
                    CodigoError.NO_ENCONTRADO.name()
            );
        }
        return paises.stream()
                .map(proveedor -> {
                    return proveedorMapper.deProveedorAProveedorResponseDto(proveedor);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorResponseDto obtenerPorId(Long id) {
        return iProveedorRepository.findById(id)
                .map(proveedor -> {
                    return proveedorMapper.deProveedorAProveedorResponseDto(proveedor);
                })
                .orElseThrow(() -> {
                   throw new ProveedorException(
                           "Proveedor no encontrado con el id: "+id,
                           HttpStatus.NOT_FOUND,
                           CodigoError.NO_ENCONTRADO.name()
                   );
                });
    }

    @Override
    @Transactional(rollbackFor = ProveedorException.class)
    public ProveedorResponseDto actualizar(Long id, ProveedorRequestDto proveedorRequestDto) {
        return iProveedorRepository.findById(id)
                .map(proveedor -> {
                    proveedor.setNombre(proveedorRequestDto.nombre());
                    proveedor.setEmail(proveedorRequestDto.email());
                    proveedor.setTelefono(proveedorRequestDto.telefono());
                    proveedor.setDireccion(proveedorRequestDto.direccion());
                    proveedor.setPais(paisService.validarPais(new MarcaRequestDto("","", proveedorRequestDto.pais())));
                    Proveedor proveedorActualizado = iProveedorRepository.save(proveedor);
                    return proveedorMapper.deProveedorAProveedorResponseDto(proveedorActualizado);
                })
                .orElseThrow(
                        () -> {
                            throw new ProveedorException(
                                    "Error al actualizar proveedor: "+proveedorRequestDto.nombre(),
                                    HttpStatus.NOT_FOUND,
                                    CodigoError.NO_ENCONTRADO.name()
                            );
                        }
                );
    }

    @Override
    public void eliminar(Long id) {
        var proveedor = iProveedorRepository.findById(id)
                .orElseThrow(
                        () -> {
                            throw new ProveedorException(
                                    "Proveedor no encontrado con id: "+id,
                                    HttpStatus.NOT_FOUND,
                                    CodigoError.NO_ENCONTRADO.name()
                            );
                        }
                );
        iProveedorRepository.delete(proveedor);
    }
}
