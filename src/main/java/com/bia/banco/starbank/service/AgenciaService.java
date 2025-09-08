package com.bia.banco.starbank.service;

import com.bia.banco.starbank.dto.AgenciaCadastroDto;
import com.bia.banco.starbank.dto.response.AgenciaResponse;


import java.util.Map;


public interface AgenciaService  {


    AgenciaResponse cadastrarAgencia(AgenciaCadastroDto dto);

    Map<String, String> calcularDistancias(double userpositionX, double userpositionY);

    void deletarAgencia(Long id);
}
