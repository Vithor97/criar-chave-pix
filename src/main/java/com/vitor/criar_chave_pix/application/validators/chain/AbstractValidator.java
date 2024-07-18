package com.vitor.criar_chave_pix.application.validators.chain;

import static java.util.Objects.nonNull;

public abstract class AbstractValidator {

    private final AbstractValidator next;

    protected AbstractValidator(AbstractValidator next) {
        this.next = next;
    }

    public abstract void validate(String tipoChave, String valorChave);
    protected void next(String tipoChave, String valorChave) {
        if(nonNull(next)){
            next.validate(tipoChave, valorChave);
        }
    }
}
