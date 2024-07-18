package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import com.vitor.criar_chave_pix.application.validators.TipoChave;

public class TipoChaveValidator extends AbstractValidator {
    public TipoChaveValidator(AbstractValidator next) {
        super(next);
    }

    @Override
    public void validate(String tipoChave, String valorChave) {
        if (!TipoChave.isValid(tipoChave)) {
            throw new ValidationException("Campo tipoChave inválido: " + tipoChave);
        }

        this.next(tipoChave, valorChave);
    }
}
