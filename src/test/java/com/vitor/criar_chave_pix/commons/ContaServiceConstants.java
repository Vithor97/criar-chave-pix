package com.vitor.criar_chave_pix.commons;

import com.vitor.criar_chave_pix.application.domain.Cliente;

public class ContaServiceConstants {

    public static final Cliente ALTERACAO_NOME_DADOS_CLIENTE_VALIDO = new Cliente(
            "Vitor",
            "Santos",
            null,
            1234,
            12345678,
            "corrente"
            );

    public static final Cliente ALTERACAO_NOME_DADOS_CLIENTE_ALTERADO = new Cliente(
            "Vitor",
            "Santos de Miranda",
            null,
            1234,
            12345678,
            "corrente"
    );

}
