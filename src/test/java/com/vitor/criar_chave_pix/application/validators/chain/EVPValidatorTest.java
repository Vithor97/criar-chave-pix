package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;

class EVPValidatorTest {

    private EVPValidator evpValidator;
    private AbstractValidator nextValidator;

    @BeforeEach
    public void setUp() {
        evpValidator = new EVPValidator();
        nextValidator = Mockito.mock(AbstractValidator.class);
        evpValidator.setNext(nextValidator);
    }

    @Test
    public void validate_invalidEVP() {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> evpValidator.validate("chave_aleatoria", "invalid EVP"))
                .withMessage("Chave aleatória inválida. Deve conter apenas letras e números.");
    }

    @Test
    public void validate_nullEVP() {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> evpValidator.validate("chave_aleatoria", null))
                .withMessage("Chave aleatória inválida. Deve conter apenas letras e números.");
    }


    @Test
    public void validate_validNonEVP() {
        evpValidator.validate("email", "test@example.com");
        verify(nextValidator).validate("email", "test@example.com");
    }

}