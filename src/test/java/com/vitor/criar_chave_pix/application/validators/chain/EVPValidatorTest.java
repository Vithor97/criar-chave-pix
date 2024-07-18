package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class EVPValidatorTest {

    private EVPValidator evpValidator;

    @BeforeEach
    public void setUp() {
        evpValidator = new EVPValidator(null);
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

}