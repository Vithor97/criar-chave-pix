package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;

public class EmailValidator extends AbstractValidator {
    @Override
    public void validate(String tipoChave, String valorChave) {
        if ("email".equalsIgnoreCase(tipoChave) &&
                (valorChave == null || !valorChave.matches("^[\\w-\\.]+@[\\w-\\.]+\\.\\w{2,}$")
                || valorChave.length() > 77)) {
            throw new ValidationException("Email inválido");
        } else if (nextValidator != null) {
            nextValidator.validate(tipoChave, valorChave);
        }
    }
}
