package com.arka.products.models.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "paises")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String codigo;

    public Pais(String nombre, String codigo){
        this.nombre = nombre;
        this.codigo = codigo;
    }

}
