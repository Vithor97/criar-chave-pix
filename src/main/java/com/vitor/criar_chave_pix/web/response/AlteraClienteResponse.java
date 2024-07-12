package com.vitor.criar_chave_pix.web.response;

import com.vitor.criar_chave_pix.application.domain.Cliente;

public record AlteraClienteResponse(
        Long id,

        String tipoConta,
        String nome,
        String sobrenome,



        Integer agencia,

        Integer conta
) {
    public static AlteraClienteResponse fromDomain(Cliente cliente) {
        return new AlteraClienteResponse(
                cliente.getId(),
                cliente.getTipoConta(),
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getAgencia(),
                cliente.getConta()
        );
    }
}
