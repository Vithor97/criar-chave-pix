package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;

public interface Validator {
    void validate(String tipoChave, String valorChave) throws ValidationException;
    void setNext(Validator nextValidator);
}
