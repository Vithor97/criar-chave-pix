package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;

public class NumeroValidator extends AbstractValidator {
    public NumeroValidator(AbstractValidator next) {
        super(next);
    }

    @Override
    public void validate(String tipoChave, String valorChave) {
        if ("celular".equalsIgnoreCase(tipoChave) && (valorChave == null || !valorChave.matches("\\+\\d{1,2}\\d{2,3}\\d{9}"))) {
            throw new ValidationException("Numero com formato inválido. Deve iniciar com “+” seguido de um DDD até 3 digitos, e de 9 digitos. Ex: +5511999999999");
        }

        this.next(tipoChave, valorChave);
    }
}
