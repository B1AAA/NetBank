package com.bia.banco.starbank.service.impl;

import com.bia.banco.starbank.dto.AgenciaCadastroDto;
import com.bia.banco.starbank.dto.DistanciaAgenciaDto;
import com.bia.banco.starbank.dto.response.AgenciaResponse;
import com.bia.banco.starbank.exception.ResourceNotFoundException;
import com.bia.banco.starbank.model.Agencia;
import com.bia.banco.starbank.repository.AgenciaRepository;
import com.bia.banco.starbank.service.AgenciaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AgenciaServiceImpl implements AgenciaService {

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Override
    public AgenciaResponse cadastrarAgencia(AgenciaCadastroDto dto) {
        Agencia agencia = new Agencia();
        agencia.setPosX(dto.getPosX());
        agencia.setPosY(dto.getPosY());

        Agencia agenciaSalva = agenciaRepository.save(agencia);

        return AgenciaResponse.builder()
                .id(agenciaSalva.getId())
                .posX(agenciaSalva.getPosX())
                .posY(agenciaSalva.getPosY())
                .build();
    }
    @Override
    @Transactional
    public Map<String, String> calcularDistancias(double posX, double posY) {
        List<Agencia> agencias = agenciaRepository.findAll();

        List<DistanciaAgenciaDto> distancias = agencias.stream()
                .map(agencia -> {
                    double distancia = calcularDistanciaEuclidiana(
                            posX, posY,
                            agencia.getPosX(), agencia.getPosY()
                    );

                    agencia.setResultado(distancia);
                    return new DistanciaAgenciaDto(agencia.getId(), distancia);
                })
                .sorted()
                .collect(Collectors.toList());


        agenciaRepository.saveAll(agencias);

        Map<String, String> resultado = new LinkedHashMap<>();
        for (int i = 0; i < distancias.size(); i++) {
            DistanciaAgenciaDto dto = distancias.get(i);
            String chave = "AGENCIA_" + dto.getAgenciaId();
            String valor = String.format("distancia = %.2f", dto.getDistancia());
            resultado.put(chave, valor);
        }

        return resultado;
    }

    private double calcularDistanciaEuclidiana(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public void deletarAgencia(Long id) {
        if (!agenciaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agência com ID " + id + " não encontrada.");
        }
        agenciaRepository.deleteById(id);
    }
}