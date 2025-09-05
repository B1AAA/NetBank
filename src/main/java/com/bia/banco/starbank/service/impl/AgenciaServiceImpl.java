package com.bia.banco.starbank.service.impl;

import com.bia.banco.starbank.dto.AgenciaCadastroDto;
import com.bia.banco.starbank.dto.DistanciaAgenciaDto;
import com.bia.banco.starbank.model.Agencia;
import com.bia.banco.starbank.repository.AgenciaRepository;
import com.bia.banco.starbank.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AgenciaServiceImpl implements AgenciaService {

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Override
    public void cadastrarAgencia(AgenciaCadastroDto dto) {
        Agencia agencia = new Agencia();
        agencia.setPosX(dto.getPosX());
        agencia.setPosY(dto.getPosY());

        agenciaRepository.save(agencia);
    }

    @Override
    public Map<String, String> calcularDistancias(double userPosX, double userPosY) {
        List<Agencia> agencias = agenciaRepository.findAll();

        List<DistanciaAgenciaDto> distancias = agencias.stream()
                .map(agencia -> {
                    double distancia = calcularDistanciaEuclidiana(
                            userPosX, userPosY,
                            agencia.getPosX(), agencia.getPosY()
                    );
                    return new DistanciaAgenciaDto(agencia.getId(), distancia);
                })
                .sorted()
                .collect(Collectors.toList());


        Map<String, String> resultado = new LinkedHashMap<>(); // LinkedHashMap mantém a ordem de inserção
        for (int i = 0; i < distancias.size(); i++) {
            DistanciaAgenciaDto dto = distancias.get(i);
            String chave = "AGENCIA " + dto.getAgenciaId();
            String valor = String.format("distancia=%.2f", dto.getDistancia());
            resultado.put(chave, valor);
        }

        return resultado;
    }

    private double calcularDistanciaEuclidiana(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}