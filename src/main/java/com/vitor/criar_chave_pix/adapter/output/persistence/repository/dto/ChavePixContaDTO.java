package com.vitor.criar_chave_pix.adapter.output.persistence.repository.dto;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChavePixContaDTO(
        UUID id,
        String tipoChave,
        String valorChave,
        String tipoConta,
        Integer agencia,
        Integer conta,
        String nomeCorrentista,
        String sobrenomeCorrentista,
        LocalDateTime dataRegistro,
        LocalDateTime dataInativacao
) {

    public static ClienteChavePix toDomain(ChavePixContaDTO dto) {
        return new ClienteChavePix(
            null,
                dto.tipoConta,
                dto.agencia,
                dto.conta,
                null,
                dto.nomeCorrentista,
                dto.sobrenomeCorrentista,
                new ChavesPix(dto.id, dto.tipoChave, dto.valorChave,dto.dataRegistro, dto.dataInativacao, true),
                null
        );
    }
}
