package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class NumeroValidatorTest {

    private NumeroValidator numeroValidator;

    @BeforeEach
    public void setUp() {
        numeroValidator = new NumeroValidator(null);
    }

    @Test
    public void validate_invalidNumero() {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> numeroValidator.validate("celular", "invalidNumber"))
                .withMessage("Numero com formato inválido. Deve iniciar com “+” seguido de um DDD até 3 digitos, e de 9 digitos. Ex: +5511999999999");
    }

    @Test
    public void validate_nullNumero() {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> numeroValidator.validate("celular", null))
                .withMessage("Numero com formato inválido. Deve iniciar com “+” seguido de um DDD até 3 digitos, e de 9 digitos. Ex: +5511999999999");
    }

    @Test
    public void validate_validNumero() {
        assertThatNoException()
                .isThrownBy(() -> numeroValidator.validate("celular", "+5511999999999"));
    }

}