package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;

public class CpfValidator extends AbstractValidator{
    @Override
    public void validate(String tipoChave, String valorChave) {
        if ("cpf".equalsIgnoreCase(tipoChave) &&
                (valorChave == null || !valorChave.matches("\\d{11}"))) {
            throw new ValidationException("CPF inválido. Deve conter 11 digitos.");
        } else if (nextValidator != null) {
            nextValidator.validate(tipoChave, valorChave);
        }
    }
}
