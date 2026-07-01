package com.cilazatta.frotacontrol.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.cilazatta.frotacontrol.dto.ViaCepResponseDto;
import com.cilazatta.frotacontrol.exeptions.BusinessException;

@Service
public class CepService {

    private final RestClient restClient;

    public CepService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public ViaCepResponseDto buscarCep(String cep) {

        cep = cep.replaceAll("\\D", "");

        ViaCepResponseDto response =
                restClient.get()
                        .uri("https://viacep.com.br/ws/{cep}/json/", cep)
                        .retrieve()
                        .body(ViaCepResponseDto.class);

        if (response == null || Boolean.TRUE.equals(response.getErro())) {
            throw new BusinessException("CEP não encontrado");
        }

        return response;
    }
}
