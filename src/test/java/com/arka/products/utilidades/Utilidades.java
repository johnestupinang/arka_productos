package com.arka.products.utilidades;

import com.arka.products.models.dtos.marca.MarcaRequestDto;
import com.arka.products.models.dtos.pais.PaisRequestDto;
import com.arka.products.models.entities.Pais;

import java.util.ArrayList;
import java.util.List;

public class Utilidades {

    private static final List<Pais> paises = new ArrayList<>();

    public Pais paisDummy(){
        return new Pais(1L, "Colombia", "COL");
    }

    public PaisRequestDto paisRequestDtoDummy (){
        return new PaisRequestDto("Colombia", "COL");
    }

    public MarcaRequestDto marcaRequestDto(){
        return new MarcaRequestDto("Colombina", "Productos comestibles", paisRequestDtoDummy());
    }

    public List<Pais> listaPaises(){
        paises.add(paisDummy());
        return paises;
    }

}
