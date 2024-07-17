package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;

public class CnpjValidator extends AbstractValidator {
    @Override
    public void validate(String tipoChave, String valorChave) {
        if ("cnpj".equalsIgnoreCase(tipoChave) &&
                (valorChave == null || !valorChave.matches("\\d{14}"))) {
            throw new ValidationException("CNPJ inválido. Deve conter 14 digitos.");
        } else if (nextValidator != null) {
            nextValidator.validate(tipoChave, valorChave);
        }
    }
}
