package com.vitor.criar_chave_pix.commons.lista;

import com.vitor.criar_chave_pix.adapter.output.persistence.repository.dto.ChavePixContaDTO;
import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ListarConstants {
    public static final UUID CHAVE_PIX_ID = UUID.randomUUID();
    public static final String TIPO_CHAVE = "CPF";
    public static final String VALOR_CHAVE = "12345678909";
    public static final String TIPO_CONTA = "corrente";
    public static final Integer AGENCIA = 1234;
    public static final Integer CONTA = 567890;
    public static final String NOME_CORRENTISTA = "Vitor";
    public static final String SOBRENOME_CORRENTISTA = "Santos De Miranda";
    public static final LocalDateTime DATA_REGISTRO = LocalDate.of(2022, 1, 1).atStartOfDay();
    public static final LocalDateTime DATA_INATIVACAO = LocalDate.of(2022, 1, 2).atStartOfDay();

    public static final ChavePixContaDTO CHAVE_PIX_CONTA_DTO = new ChavePixContaDTO(
            CHAVE_PIX_ID, TIPO_CHAVE, VALOR_CHAVE, TIPO_CONTA, AGENCIA, CONTA,
            NOME_CORRENTISTA, SOBRENOME_CORRENTISTA, DATA_REGISTRO, DATA_INATIVACAO
    );

    public static ClienteChavePix toDomain(ChavePixContaDTO dto) {
        return new ClienteChavePix(
                null, dto.tipoConta(), dto.agencia(), dto.conta(), null,
                dto.nomeCorrentista(), dto.sobrenomeCorrentista(),
                new ChavesPix(dto.id(), dto.tipoChave(), dto.valorChave(), dto.dataRegistro(), dto.dataInativacao(), true),
                null
        );
    }
}
