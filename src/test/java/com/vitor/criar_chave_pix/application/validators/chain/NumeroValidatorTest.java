package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.verify;

class NumeroValidatorTest {

    private NumeroValidator numeroValidator;
    private AbstractValidator nextValidator;

    @BeforeEach
    public void setUp() {
        numeroValidator = new NumeroValidator();
        nextValidator = Mockito.mock(AbstractValidator.class);
        numeroValidator.setNext(nextValidator);
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

    @Test
    public void validate_validNonNumero() {
        numeroValidator.validate("email", "test@example.com");
        verify(nextValidator).validate("email", "test@example.com");
    }

}