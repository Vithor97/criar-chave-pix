package com.vitor.criar_chave_pix.commons.consultacontantes;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ConsultaContantes {

    public static final UUID DEFAULT_UUID = UUID.fromString("7bed4ffa-7ab1-4a92-a3ff-43564426414b");
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final LocalDateTime DATA_REGISTRO = LocalDateTime.parse("14/07/2024 19:28:37", FORMATTER);
    public static final LocalDateTime DATA_INATIVACAO = LocalDateTime.parse("14/07/2024 19:28:54", FORMATTER);

    public static ChavesPix CRIACHAVEPIX_INATIVADA_DEFAULT=
         ChavesPix.builder()
                .uuidChave(DEFAULT_UUID)
                .tipoChave("chave_aleatoria")
                .valorChave("chavealeatoriaDavi3")
                .dataCriacao(DATA_REGISTRO)
                .dataInativacao(DATA_INATIVACAO)
                .build();


    public static final ClienteChavePix CRIACLIENTE_CHAVEPIX_INATIVADA_DEFAULT=
         ClienteChavePix.builder()
                .idConta(1L)
                .tipoConta("corrente")
                .agencia(1234)
                .conta(11234441)
                .tipoPessoa("PF")
                .nomeCorrentista("Davi")
                .sobrenomeCorrentista("Santos")
                .chavesPix(CRIACHAVEPIX_INATIVADA_DEFAULT)
                .build();


    public static final ClienteChavePix CLIENTE_CHAVEPIX_1 = ClienteChavePix.builder()
            .idConta(1L)
            .tipoConta("corrente")
            .agencia(1234)
            .conta(11234441)
            .tipoPessoa("PF")
            .nomeCorrentista("Ana")
            .sobrenomeCorrentista("Borges")
            .chavesPix(CRIACHAVEPIX_INATIVADA_DEFAULT)
            .build();

    public static final ClienteChavePix CLIENTE_CHAVEPIX_2 = ClienteChavePix.builder()
            .idConta(2L)
            .tipoConta("poupanca")
            .agencia(5678)
            .conta(87654321)
            .tipoPessoa("PJ")
            .nomeCorrentista("Bruno")
            .sobrenomeCorrentista("Silva")
            .chavesPix(CRIACHAVEPIX_INATIVADA_DEFAULT)
            .build();

    public static final ClienteChavePix CLIENTE_CHAVEPIX_3 = ClienteChavePix.builder()
            .idConta(3L)
            .tipoConta("corrente")
            .agencia(91011)
            .conta(12131415)
            .tipoPessoa("PF")
            .nomeCorrentista("Carlos")
            .sobrenomeCorrentista("Dantas")
            .chavesPix(CRIACHAVEPIX_INATIVADA_DEFAULT)
            .build();

}
