package com.bia.banco.starbank.controller;

import com.bia.banco.starbank.dto.AgenciaCadastroDto;
import com.bia.banco.starbank.dto.response.AgenciaResponse;
import com.bia.banco.starbank.exception.BadRequestException;
import com.bia.banco.starbank.service.AgenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/desafio")
@Tag(name = "Agência", description = "Operações relacionadas ao cadastro e consulta de agências")
public class AgenciaController {

    @Autowired
    private AgenciaService agenciaService;

    @GetMapping("/distancia")
    @Operation(summary = "Consultar distâncias das agências", description = "Retorna um mapa ordenado com a distância de cada agência até o ponto do usuário.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Coordenadas inválidas")
    public ResponseEntity<Map<String, String>> calcularDistancias(@Parameter(description = "Coordenada X do usuário", example = "-10.0") @RequestParam double posX,
                                                                  @Parameter(description = "Coordenada Y do usuário", example = "5.0") @RequestParam double posY) {

        if (Math.abs(posX) > 180 || Math.abs(posY) > 90) {
            throw new BadRequestException("Coordenadas inválidas. Longitude deve estar entre -180 e 180, e Latitude entre -90 e 90.");
        }
        Map<String, String> resultado = agenciaService.calcularDistancias(posX, posY);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar uma nova agência", description = "Registra uma agência com suas coordenadas X e Y no sistema.")
    @ApiResponse(responseCode = "201", description = "Agência criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<AgenciaResponse> cadastrarAgencia(@RequestBody AgenciaCadastroDto dto) {

        if (dto.getPosX() == null || dto.getPosY() == null) {
            throw new BadRequestException("Coordenadas 'posX' e 'posY' são obrigatórias e não podem ser nulas.");
        }
        AgenciaResponse agenciaCriada = agenciaService.cadastrarAgencia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(agenciaCriada);
    }

    @DeleteMapping("/agencia/{id}")
    @Operation(summary = "Deletar uma agência", description = "Remove uma agência do sistema pelo seu ID.")
    @ApiResponse(responseCode = "204", description = "Agência deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Agência não encontrada")
    public ResponseEntity<Void> deletarAgencia(@PathVariable Long id) {
        agenciaService.deletarAgencia(id);
        return ResponseEntity.noContent().build();
    }
}
