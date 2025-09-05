package com.bia.banco.starbank.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DistanciaAgenciaDto implements Comparable<DistanciaAgenciaDto> {

    private Long agenciaId;
    private Double distancia;

    @Override
    public int compareTo(DistanciaAgenciaDto outro) {
        return this.distancia.compareTo(outro.distancia);
    }
}
