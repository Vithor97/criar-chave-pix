package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class EmailValidatorTest {

    private EmailValidator emailValidator;

    @BeforeEach
    public void setUp() {
        emailValidator = new EmailValidator(null);
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


}