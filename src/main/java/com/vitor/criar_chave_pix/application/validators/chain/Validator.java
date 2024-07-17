package com.vitor.criar_chave_pix.application.validators.chain;

public interface Validator {
    void validate(String tipoChave, String valorChave);
    void setNext(Validator nextValidator);
}
