package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;

public class CnpjValidator extends AbstractValidator {
    public CnpjValidator(AbstractValidator next) {
        super(next);
    }

    @Override
    public void validate(String tipoChave, String valorChave) {
        if ("cnpj".equalsIgnoreCase(tipoChave) &&
                (valorChave == null || !valorChave.matches("\\d{14}"))) {
            throw new ValidationException("CNPJ inválido. Deve conter 14 digitos.");
        }

        this.next(tipoChave, valorChave);
    }
}
