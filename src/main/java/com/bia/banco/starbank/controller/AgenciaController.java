package com.bia.banco.starbank.controller;

import com.bia.banco.starbank.dto.AgenciaCadastroDto;
import com.bia.banco.starbank.dto.response.AgenciaResponse;
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

    /**
     * Endpoint POST para cadastrar uma nova agência.
     * Retorna 201 Created com os dados da agência criada.
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<AgenciaResponse> cadastrarAgencia(@RequestBody AgenciaCadastroDto dto) {
        AgenciaResponse agenciaCriada = agenciaService.cadastrarAgencia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(agenciaCriada);
    }

    /**
     * Endpoint GET para calcular distâncias das agências ao ponto do usuário.
     * Retorna 200 OK com o Map de distâncias.
     */
    @GetMapping("/distancia")
    public ResponseEntity<Map<String, String>> calcularDistancias(
            @RequestParam double posX,
            @RequestParam double posY) {

        Map<String, String> resultado = agenciaService.calcularDistancias(posX, posY);
        return ResponseEntity.ok(resultado);
    }
}
