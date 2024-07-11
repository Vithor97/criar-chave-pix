package com.vitor.criar_chave_pix.application.ports;

import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;

public interface ContaServicePort {

    ClienteChavePix buscaCliente(Integer agencia, Integer conta);

    Cliente criaCliente(Cliente cliente);

    String verificaTipoPessoa(Integer agencia, Integer conta);
}
