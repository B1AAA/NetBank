package com.bia.banco.starbank.service;

import com.bia.banco.starbank.dto.AgenciaCadastroDto;
import com.bia.banco.starbank.dto.response.AgenciaResponse;
import com.bia.banco.starbank.service.impl.AgenciaServiceImpl;

import java.util.Map;


public interface AgenciaService  {


    AgenciaResponse cadastrarAgencia(AgenciaCadastroDto dto);

    Map<String, String> calcularDistancias(double userPosX, double userPosY);

    void deletarAgencia(Long id);
}
