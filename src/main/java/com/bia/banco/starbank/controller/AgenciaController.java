package com.bia.banco.starbank.controller;

import com.bia.banco.starbank.dto.AgenciaCadastroDto;
import com.bia.banco.starbank.dto.response.AgenciaResponse;
import com.bia.banco.starbank.exception.BadRequestException;
import com.bia.banco.starbank.service.AgenciaService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/desafio")
public class AgenciaController {

    @Autowired
    private AgenciaService agenciaService;


    @PostMapping("/cadastrar")
    public ResponseEntity<AgenciaResponse> cadastrarAgencia(@RequestBody AgenciaCadastroDto dto) {

        if (dto.getPosX() == null || dto.getPosY() == null) {
            throw new BadRequestException("Coordenadas 'posX' e 'posY' são obrigatórias e não podem ser nulas.");
        }


        AgenciaResponse agenciaCriada = agenciaService.cadastrarAgencia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(agenciaCriada);
    }

    @GetMapping("/distancia")
    public ResponseEntity<Map<String, String>> calcularDistancias(
            @RequestParam double posX,
            @RequestParam double posY) {

        Map<String, String> resultado = agenciaService.calcularDistancias(posX, posY);
        return ResponseEntity.ok(resultado);
    }
}
