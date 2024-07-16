package com.vitor.criar_chave_pix.adapter.input.web.response;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record ConsultaChavePixResponse(
        UUID id,
        String tipoChave,
        String valorChave,
        String tipoConta,
        Integer agencia,
        Integer conta,
        String nomeCorrentista,
        String sobrenomeCorrentista,
        String dataInclusao,
        String dataInativacao
) {
    public static ConsultaChavePixResponse fromDomain(ClienteChavePix clienteChavePix) {
        ChavesPix chave = clienteChavePix.getChavesPix();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return new ConsultaChavePixResponse(
                chave.getUuidChave(),
                chave.getTipoChave(),
                chave.getValorChave(),
                clienteChavePix.getTipoConta(),
                clienteChavePix.getAgencia(),
                clienteChavePix.getConta(),
                clienteChavePix.getNomeCorrentista(),
                clienteChavePix.getSobrenomeCorrentista() != null ? clienteChavePix.getSobrenomeCorrentista() : "",
                chave.getDataCriacao() != null ? chave.getDataCriacao().format(formatter) : "",
                chave.getDataInativacao() != null ? chave.getDataInativacao().format(formatter) : ""
        );
    }
}
