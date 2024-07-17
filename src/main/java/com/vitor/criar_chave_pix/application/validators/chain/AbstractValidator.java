package com.vitor.criar_chave_pix.application.validators.chain;

public abstract class AbstractValidator implements Validator {
    protected Validator nextValidator;

    @Override
    public void setNext(Validator nextValidator) {
        this.nextValidator = nextValidator;
    }
}
