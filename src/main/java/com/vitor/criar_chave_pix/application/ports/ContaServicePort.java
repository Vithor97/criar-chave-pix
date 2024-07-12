package com.vitor.criar_chave_pix.application.ports;

import com.vitor.criar_chave_pix.application.domain.Cliente;

public interface ContaServicePort {

    String verificaTipoPessoa(Integer agencia, Integer conta);

    Cliente alteraDadosConta(Long id, Cliente cliente);

}
