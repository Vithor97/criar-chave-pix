package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class TipoChaveValidatorTest {

    private TipoChaveValidator tipoChaveValidator;

    @BeforeEach
    public void setUp() {
        tipoChaveValidator = new TipoChaveValidator(null);
    }

    @Test
    public void validate_invalidTipoChave() {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> tipoChaveValidator.validate("invalidTipo", "valorChave"))
                .withMessage("Campo tipoChave inválido: invalidTipo");
    }

    @Test
    public void validate_validTipoChave() {
        assertThatNoException()
                .isThrownBy(() -> tipoChaveValidator.validate("celular", "valorChave"));
    }

}