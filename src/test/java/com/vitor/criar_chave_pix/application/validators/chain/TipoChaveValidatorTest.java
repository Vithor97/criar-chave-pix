package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.verify;

class TipoChaveValidatorTest {

    private TipoChaveValidator tipoChaveValidator;
    private AbstractValidator nextValidator;

    @BeforeEach
    public void setUp() {
        tipoChaveValidator = new TipoChaveValidator();
        nextValidator = Mockito.mock(AbstractValidator.class);
        tipoChaveValidator.setNext(nextValidator);
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

    @Test
    public void validate_validTipoChave_passToNextValidator() {
        tipoChaveValidator.validate("email", "test@example.com");
        verify(nextValidator).validate("email", "test@example.com");
    }
}