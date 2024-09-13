package com.clinica.dh.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "domicilios")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String via;
    private int numero;
    private String barrio;
    private String region;

    @Override
    public String toString() {
        return "Domicilio{" +
                "id=" + id +
                ", via='" + via + '\'' +
                ", numero=" + numero +
                ", barrio='" + barrio + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
