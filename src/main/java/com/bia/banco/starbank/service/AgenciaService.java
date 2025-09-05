package com.bia.banco.starbank.service;

import com.bia.banco.starbank.dto.AgenciaCadastroDto;
import com.bia.banco.starbank.service.impl.AgenciaServiceImpl;

import java.util.Map;


public interface AgenciaService  {


    public void cadastrarAgencia(AgenciaCadastroDto dto);
    Map<String, String> calcularDistancias(double userPosX, double userPosY);
}
