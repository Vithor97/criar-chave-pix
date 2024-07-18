package com.vitor.criar_chave_pix.application.validators.chain;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class CnpjValidatorTest {

    private CnpjValidator cnpjValidator;

    @BeforeEach
    public void setUp() {
        cnpjValidator = new CnpjValidator(null);
    }

    @Test
    public void validate_invalidCnpj() {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> cnpjValidator.validate("cnpj", "invalidCNPJ"))
                .withMessage("CNPJ inválido. Deve conter 14 digitos.");
    }

    @Test
    public void validate_nullCnpj() {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> cnpjValidator.validate("cnpj", null))
                .withMessage("CNPJ inválido. Deve conter 14 digitos.");
    }

    @Test
    public void validate_validCnpj() {
        assertThatNoException()
                .isThrownBy(() -> cnpjValidator.validate("cnpj", "12345678000195"));
    }

}