package com.vitor.criar_chave_pix.commons;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ChavePixServiceContants {

    public static final ClienteChavePix CLIENTE_CHAVE_PIXV_VALIDO = new ClienteChavePix(null,
            "corrente",
            1234,
            12345678,
            "PF",
            "Vitor",
            "Silva",
            new ChavesPix(null, "cpf", "99999999999", null, null, true),
            null
    );


    public static final ClienteChavePix CLIENTE_EXISTENTE_CHAVE_PIXV_VALIDO = new ClienteChavePix(
            1L,
            "corrente",
            1234,
            12345678,
            "PF",
            "Vitor",
            "Silva",
            new ChavesPix(UUID.randomUUID(), "cpf", "99999999999", LocalDateTime.now(), null, true),
            null
    );

    public static final ClienteChavePix CLIENTE_EXISTENTE_CHAVE_PIX_EMAIL_VALIDO = new ClienteChavePix(
            1L,
            "corrente",
            1234,
            12345678,
            "PJ",
            "Vitor",
            "Silva",
            new ChavesPix(UUID.randomUUID(), "email", "vitor@email.com", LocalDateTime.now(), null, true),
            null
    );


    public static final ClienteChavePix CLIENTE_EXISTENTE_CHAVEPIX_CPF_EXISTENTE_INVALIDO_ = new ClienteChavePix(
            1L,
            "corrente",
            1234,
            12345678,
            "PF",
            "Vitor",
            "Silva",
            new ChavesPix(UUID.randomUUID(), "cpf", "99999999999", LocalDateTime.now(), null, true),
            List.of(new ChavesPix(UUID.randomUUID(), "cpf", "99999999998", LocalDateTime.now(), null, true))
    );

    public static final ClienteChavePix CLIENTE_PF_EXISTENTE_MAX5CHAVES_CADASTRADAS_RETONAEXCECAO = new ClienteChavePix(
            1L,
            "corrente",
            1234,
            12345678,
            "PF",
            "Vitor",
            "Silva",
            new ChavesPix(UUID.randomUUID(), "email", "vitor99@email.com", LocalDateTime.now(), null, true),
            List.of(new ChavesPix(UUID.randomUUID(), "cpf", "99999999999", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor1@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor2@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor3@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor4@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor5@email.com", LocalDateTime.now(), null, true)
            )
    );

    public static final ClienteChavePix CLIENTE_PJ_EXISTENTE_MAX20CHAVES_CADASTRADAS_RETONAEXCECAO = new ClienteChavePix(
            1L,
            "corrente",
            1234,
            12345678,
            "PJ",
            "Vitor",
            "Silva",
            new ChavesPix(UUID.randomUUID(), "email", "vitor99@email.com", LocalDateTime.now(), null, true),
            List.of(
                    new ChavesPix(UUID.randomUUID(), "cnpj", "99999999999999", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor1@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor2@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor3@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor4@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor5@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor6@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor7@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor8@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor9@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor10@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor11@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor12@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor13@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor14@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor15@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor16@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor17@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor18@email.com", LocalDateTime.now(), null, true),
                    new ChavesPix(UUID.randomUUID(), "email", "vitor19@email.com", LocalDateTime.now(), null, true)
            )
    );
}
