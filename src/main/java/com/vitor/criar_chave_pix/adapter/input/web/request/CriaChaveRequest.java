package com.vitor.criar_chave_pix.adapter.input.web.request;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import jakarta.validation.constraints.*;

import java.util.Collections;

public record CriaChaveRequest (

        @NotNull
        @NotBlank
        String tipoChave,

        @NotNull
        @NotBlank
        String valorChave,
        @NotNull
        @NotBlank
        @Pattern(regexp = "corrente|poupanca", message = "Tipo de conta deve ser 'corrente' ou 'poupanca'")
        String tipoConta,
        @NotNull
        @Max(value = 9999, message = "Número da agência não deve estourar 4 dígitos")
        Integer agencia,

        @NotNull
        @Max(value = 99999999, message = "Número da conta não deve estourar 8 dígitos")
        Integer conta,
        @NotNull
        @NotBlank
        @Size(max = 30, message = "Nome do correntista não deve exceder 30 caracteres")
        String nomeCorrentista,

        @Size(max = 45, message = "Sobrenome do correntista não deve exceder 45 caracteres")
        String sobrenomeCorrentista

) {

        public ClienteChavePix toDomain() {
                return new ClienteChavePix(
                        null,
                        tipoConta,
                        agencia,
                        conta,
                        null,
                        nomeCorrentista,
                        sobrenomeCorrentista,
                        new ChavesPix(null, tipoChave, valorChave, null, null, true),
                        Collections.emptyList() // Inicialmente a lista de chaves Pix é vazia
                );
        }
}
