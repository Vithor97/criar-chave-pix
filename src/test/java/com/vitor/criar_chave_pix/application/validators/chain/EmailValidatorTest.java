package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.verify;

class EmailValidatorTest {

    private EmailValidator emailValidator;
    private AbstractValidator nextValidator;

    @BeforeEach
    public void setUp() {
        emailValidator = new EmailValidator();
        nextValidator = Mockito.mock(AbstractValidator.class);
        emailValidator.setNext(nextValidator);
    }

    @Test
    public void validate_invalidEmail() {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> emailValidator.validate("email", "invalidEmail"))
                .withMessage("Email inválido");
    }

    @Test
    public void validate_nullEmail() {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> emailValidator.validate("email", null))
                .withMessage("Email inválido");
    }

    @Test
    public void validate_emailTooLong() {
        String longEmail = "a".repeat(78) + "@example.com";
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> emailValidator.validate("email", longEmail))
                .withMessage("Email inválido");
    }

    @Test
    public void validate_validEmail() {
        assertThatNoException()
                .isThrownBy(() -> emailValidator.validate("email", "test@example.com"));
    }

    @Test
    public void validate_validNonEmail() {
        emailValidator.validate("cpf", "12345678901");
        verify(nextValidator).validate("cpf", "12345678901");
    }

}