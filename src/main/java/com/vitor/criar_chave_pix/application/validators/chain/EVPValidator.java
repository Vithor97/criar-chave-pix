package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;

public class EVPValidator extends AbstractValidator {
    @Override
    public void validate(String tipoChave, String valorChave) {
        if ("chave_aleatoria".equalsIgnoreCase(tipoChave) &&
                (valorChave == null || !valorChave.matches("^[a-zA-Z0-9]{1,36}$"))) {
            throw new ValidationException("Chave aleatória inválida. Deve conter apenas letras e números.");
        } else if (nextValidator != null) {
            nextValidator.validate(tipoChave, valorChave);
        }
    }
}
