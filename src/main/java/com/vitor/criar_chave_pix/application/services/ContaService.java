package com.vitor.criar_chave_pix.application.services;

import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.application.ports.ContaServicePort;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ContaService implements ContaServicePort {
    @Override
    public ClienteChavePix buscaCliente(Integer agencia, Integer conta) {
        return null;
    }

    @Override
    public Cliente criaCliente(Cliente cliente) {


        return null;
    }

    @Override
    public String verificaTipoPessoa(Integer agencia, Integer conta) {
        //chama API externa para saber se PF ou PJ
        return new Random().nextBoolean() ? "PF" : "PJ";
    }
}
