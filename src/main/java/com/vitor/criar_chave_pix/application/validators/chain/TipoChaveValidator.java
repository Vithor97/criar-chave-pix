package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import com.vitor.criar_chave_pix.application.validators.TipoChave;

class TipoChaveValidator extends AbstractValidator {
    @Override
    public void validate(String tipoChave, String valorChave) throws ValidationException {
        if (!TipoChave.isValid(tipoChave)) {
            throw new ValidationException("Campo tipoChave inválido: " + tipoChave);
        } else if (nextValidator != null) {
            nextValidator.validate(tipoChave, valorChave);
        }
    }
}
