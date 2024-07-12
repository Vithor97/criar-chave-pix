package com.vitor.criar_chave_pix.web.request;

import com.vitor.criar_chave_pix.application.domain.Cliente;
import jakarta.validation.constraints.*;

public record AlteraClienteRequest(
        @NotBlank(message = "Nome do correntista é obrigatório")
        @Size(max = 30, message = "Nome do correntista não deve exceder 30 caracteres")
        String nome,

        @Size(max = 45, message = "Sobrenome do correntista não deve exceder 45 caracteres")
        String sobrenome,

        @NotBlank(message = "Tipo de conta é obrigatório")
        @Pattern(regexp = "corrente|poupanca", message = "Tipo de conta deve ser 'corrente' ou 'poupanca'")
        @Size(max = 10, message = "Tipo de conta não deve exceder 10 caracteres")
        String tipoConta,

        @NotNull
        @Max(value = 9999, message = "Número da agência não deve estourar 4 dígitos")
        Integer agencia,

        @NotNull
        @Max(value = 99999999, message = "Número da conta não deve estourar 8 dígitos")
        Integer conta
) {
    public Cliente toDomain() {
        return new Cliente(nome, sobrenome, null, agencia, conta, tipoConta);
    }
}
