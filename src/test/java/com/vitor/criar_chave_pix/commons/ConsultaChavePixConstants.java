package com.vitor.criar_chave_pix.commons;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConsultaChavePixConstants {

    public static final ChavesPix CHAVE_PIX_CPF_VALIDO = ChavesPix.builder()
            .uuidChave(UUID.fromString("7bed4ffa-7ab1-4a92-a3ff-43564426414b"))
            .chaveAtivo(true)
            .tipoChave("cpf")
            .valorChave("99999999999")
            .dataInativacao(null)
            .dataCriacao(LocalDateTime.now())
            .build();

    public static final ChavesPix CHAVE_PIX_CPF_DESATIVADO = ChavesPix.builder()
            .uuidChave(UUID.fromString("7bed4ffa-7ab1-4a92-a3ff-43564426414b"))
            .chaveAtivo(false)
            .tipoChave("cpf")
            .valorChave("99999999999")
            .dataInativacao(LocalDateTime.now())
            .dataCriacao(LocalDateTime.now().minusMonths(6))
            .build();
    public static final ClienteChavePix CLIENTE_CHAVE_PIX_CPF_VALIDO = ClienteChavePix.builder()
            .idConta(1L)
            .agencia(1234)
            .conta(12345678)
            .tipoPessoa("PF")
            .nomeCorrentista("Vitor")
            .sobrenomeCorrentista("Santos")
            .chavesPix(CHAVE_PIX_CPF_VALIDO)
            .chavesPixList(null)
            .build();

    public static final ClienteChavePix CLIENTE_CHAVE_PIX_CPF_DESATIVADO = ClienteChavePix.builder()
            .idConta(1L)
            .tipoConta("corrente")
            .agencia(1234)
            .conta(12345678)
            .tipoPessoa("PF")
            .nomeCorrentista("Vitor")
            .sobrenomeCorrentista("Santos")
            .chavesPix(CHAVE_PIX_CPF_DESATIVADO)
            .chavesPixList(null)
            .build();
}
