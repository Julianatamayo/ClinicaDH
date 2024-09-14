package com.clinica.dh.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "La vía no puede estar vacía")
    @Size(max = 100, message = "La longitud máxima de la vía es 100 caracteres")
    private String via;

    @NotNull(message = "El número no puede ser nulo")
    private int numero;

    @NotBlank(message = "El barrio no puede estar vacío")
    @Size(max = 100, message = "La longitud máxima del barrio es 100 caracteres")
    private String barrio;

    @NotBlank(message = "La región no puede estar vacía")
    @Size(max = 100, message = "La longitud máxima de la región es 100 caracteres")
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

