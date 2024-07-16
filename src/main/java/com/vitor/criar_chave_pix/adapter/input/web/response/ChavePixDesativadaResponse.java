package com.vitor.criar_chave_pix.adapter.input.web.response;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record ChavePixDesativadaResponse(
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
    public static ChavePixDesativadaResponse fromDomain(ClienteChavePix clienteChavePix) {
        ChavesPix chavesPix = clienteChavePix.getChavesPix();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String dataInclusao = clienteChavePix.getChavesPix().getDataCriacao() != null ?
                clienteChavePix.getChavesPix().getDataCriacao().format(formatter) : "";

        String dataInativacao = clienteChavePix.getChavesPix().getDataInativacao() != null ?
                clienteChavePix.getChavesPix().getDataInativacao().format(formatter) : "";

        return new ChavePixDesativadaResponse(
                chavesPix.getUuidChave(),
                chavesPix.getTipoChave(),
                chavesPix.getValorChave(),
                clienteChavePix.getTipoConta(),
                clienteChavePix.getAgencia(),
                clienteChavePix.getConta(),
                clienteChavePix.getNomeCorrentista(),
                clienteChavePix.getSobrenomeCorrentista(),
                dataInclusao,
                dataInativacao
        );
    }
}
